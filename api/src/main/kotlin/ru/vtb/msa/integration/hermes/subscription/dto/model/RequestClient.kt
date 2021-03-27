package ru.vtb.msa.integration.hermes.subscription.dto.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@ApiModel(description = "Информация о клиенте")
data class RequestClient(
        @ApiModelProperty(value = "Глобальный идентификатор клиента")
        val globalId: String?,
        @field:NotBlank(message = "is empty")
        @ApiModelProperty(value = "Индентификатор системы клиента", example = "91000")
        val systemId: String,
        @field:NotBlank(message = "is empty")
        @ApiModelProperty(value = "Тип системы инициатора запроса", example = "000")
        val type: String,
        @field:NotBlank(message = "is empty")
        @ApiModelProperty(value = "Идентификатор клиента в вызывающей системе", example = "20014343")
        val number: String
)