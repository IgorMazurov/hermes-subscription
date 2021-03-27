package ru.vtb.msa.integration.hermes.subscription.dto.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Информация об ошибке")
data class HermesException(
        @ApiModelProperty(value = "Код ошибки", example = "REQUIRED_PARAMETER_IS_NOT_SENT")
        val code: String,
        @ApiModelProperty(value = "Текст ошибки", example = "validation failure. field 'client.type': is empty")
        val description: String?
)