package ru.vtb.msa.integration.hermes.subscription.dto.request

import io.swagger.annotations.ApiModel
import ru.vtb.msa.integration.hermes.subscription.dto.model.CalculationClient
import ru.vtb.msa.integration.hermes.subscription.dto.model.InitiatorProcess
import ru.vtb.msa.integration.hermes.subscription.dto.model.RequestClient
import javax.validation.Valid

sealed class HermesRequest

@ApiModel(description = "Запрос на получения способов уведомления клиента")
data class GetNotificationObjectsRequest(
        val initiatorProcess: InitiatorProcess?,
        @field:Valid
        val client: RequestClient
) : HermesRequest()


@ApiModel(description = "Запрос на получение активного пакета СМС")
data class GetActiveSmsNotificationPacketRequest(
        val initiatorProcess: InitiatorProcess?,
        @field:Valid
        val client: RequestClient
) : HermesRequest()


@ApiModel(description = "Запрос на получение активного пакета СМС")
data class CalculateCommissionRequest(
        val initiatorProcess: InitiatorProcess?,
        @field:Valid
        val client: CalculationClient
) : HermesRequest()