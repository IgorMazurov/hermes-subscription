package ru.vtb.msa.integration.hermes.subscription.dto.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(description = "Информация о инициирующем процессе")
data class InitiatorProcess(
        @ApiModelProperty(value = "Идентификатор системы", example = "TB")
        val systemId: String?,
        @ApiModelProperty(value = "Идентификатор процесса", example = "GetNotificationPacketByClient")
        val processId: String?,
        @ApiModelProperty(value = "Наименование пользователя")
        val userName: String?
)