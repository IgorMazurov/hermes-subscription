package ru.vtb.msa.integration.hermes.subscription.dto.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException

@ApiModel(description = "Ответ на запрос получения активного пакета СМС")
data class GetActiveSmsNotificationPacketResponse(
        @ApiModelProperty(value = "Код пакета нотификации", example = "Telebank-sms")
        val activeSmsNotificationPacket: String?,
        val exception: HermesException?
)