package ru.vtb.msa.integration.hermes.subscription.dto.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import ru.vtb.msa.integration.hermes.subscription.dto.model.CalculationNotificationPacket
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException

@ApiModel(description = "Ответ на запрос получения параметров комиссии")
data class CalculateCommissionResponse(
        @ApiModelProperty(value = "Код пакета нотификации", example = "Telebank-sms")
        val notificationPackets: List<CalculationNotificationPacket>?,
        val exception: HermesException?
)