package ru.vtb.msa.integration.hermes.subscription.annotation

import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import ru.vtb.msa.integration.hermes.subscription.dto.response.HermesErrorResponse

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ApiResponses(value = [
    ApiResponse(code = 400, response = HermesErrorResponse::class, message = "Ошибка валидации"),
    ApiResponse(code = 500, response = HermesErrorResponse::class, message = "Внутреняя ошибка сервера или Hermes недоступен/вернул ошибку")
])
annotation class ApiErrorResponses