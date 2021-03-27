package ru.vtb.msa.integration.hermes.subscription.config

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import ru.sokomishalov.commons.core.log.Loggable
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException
import ru.vtb.msa.integration.hermes.subscription.dto.response.HermesErrorResponse
import ru.vtb.msa.integration.hermes.subscription.exception.HermesNotAnswerException
import ru.vtb.msa.integration.hermes.subscription.util.findCause
import javax.validation.ConstraintViolationException

@ControllerAdvice
class HermesExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<HermesErrorResponse> {
        logError("Bad Request", e)
        val message = when (val cause = e.cause) {
            is InvalidFormatException -> "Can't read value ${cause.value} for field ${cause.path.errorPath()}"
            is MissingKotlinParameterException -> "Missing required field ${cause.path.errorPath()}"
            else -> "request body missing"
        }
        return status(BAD_REQUEST).body(HermesErrorResponse(exception = HermesException(REQUIRED_PARAMETER_IS_NOT_SENT, message)))
    }

    private fun List<JsonMappingException.Reference>.errorPath(): String {
        return this.joinToString(separator = "->") { it.fieldName ?: it.index.toString() ?: "unknown" }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<HermesErrorResponse> {
        logError("Bad Request", e)
        val message = e.bindingResult.allErrors.joinToString(prefix = "validation failure. ") {
            when (it) {
                is FieldError -> "field '${it.field}': ${it.defaultMessage}"
                else -> it.defaultMessage.orEmpty()
            }
        }
        return status(BAD_REQUEST).body(HermesErrorResponse(exception = HermesException(REQUIRED_PARAMETER_IS_NOT_SENT, message)))
    }

    @ExceptionHandler(
            ConstraintViolationException::class
    )
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<HermesErrorResponse> {
        logError("Bad Request", e)
        val message = e.constraintViolations
                .joinToString { "Property ${it.propertyPath} has an error: ${it.message}" }
        return status(BAD_REQUEST).body(HermesErrorResponse(exception = HermesException(REQUIRED_PARAMETER_IS_NOT_SENT, message)))
    }

    @ExceptionHandler(
            IllegalArgumentException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun badRequest(e: Exception): ResponseEntity<HermesErrorResponse> {
        val cause = e.findCause()
        logError("Bad Request", cause)
        return status(BAD_REQUEST).body(HermesErrorResponse(exception = HermesException(REQUIRED_PARAMETER_IS_NOT_SENT, cause.message)))
    }

    @ExceptionHandler(
            HermesNotAnswerException::class
    )
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun hermesNotAnswer(e: HermesNotAnswerException): ResponseEntity<HermesErrorResponse> {
        logError("Internal server error", e)
        return status(INTERNAL_SERVER_ERROR).body(HermesErrorResponse(exception = HermesException(INNER_HERMES_ERROR_CODE, e.message)))
    }

    @ExceptionHandler(
            Error::class,
            Exception::class
    )
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun internalServerError(e: Exception): ResponseEntity<HermesErrorResponse> {
        logError("Internal server error", e)
        return status(INTERNAL_SERVER_ERROR)
                .body(
                        HermesErrorResponse(
                                exception = HermesException(
                                        INTERNAL_SERVER_ERROR_CODE,
                                        e.message ?: e.cause?.message
                                )
                        )
                )
    }

    companion object : Loggable
}

const val INNER_HERMES_ERROR_CODE = "INNER_HERMES_ERROR"
private const val REQUIRED_PARAMETER_IS_NOT_SENT = "REQUIRED_PARAMETER_IS_NOT_SENT"
private const val INTERNAL_SERVER_ERROR_CODE = "INTEGRATION_HERMES_INTERNAL_SERVER_ERROR "