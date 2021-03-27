package ru.vtb.msa.integration.hermes.subscription.dto.response

import io.swagger.annotations.ApiModel
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException
import ru.vtb.msa.integration.hermes.subscription.dto.model.ResponseClient

@ApiModel(description = "Ответ на запрос получения способов уведомления клиента")
data class GetNotificationObjectsResponse(
        val client: ResponseClient?,
        val exception: HermesException?
)