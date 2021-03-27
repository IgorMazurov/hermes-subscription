package ru.vtb.msa.integration.hermes.subscription.web

import org.springframework.web.bind.annotation.RestController
import ru.vtb.msa.integration.hermes.subscription.api.IntegrationHermesSubscriptionApi
import ru.vtb.msa.integration.hermes.subscription.dto.request.CalculateCommissionRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetActiveSmsNotificationPacketRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetNotificationObjectsRequest
import ru.vtb.msa.integration.hermes.subscription.dto.response.CalculateCommissionResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetActiveSmsNotificationPacketResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetNotificationObjectsResponse
import ru.vtb.msa.integration.hermes.subscription.service.IntegrationHermesService

@RestController
class IntegrationHermesController(
        private val hermesService: IntegrationHermesService
) : IntegrationHermesSubscriptionApi {

    override fun getActiveSmsNotificationPacket(
            request: GetActiveSmsNotificationPacketRequest
    ): GetActiveSmsNotificationPacketResponse = hermesService.process(request)

    override fun getNotificationObjects(
            request: GetNotificationObjectsRequest
    ): GetNotificationObjectsResponse = hermesService.process(request)

    override fun calculateCommission(
            request: CalculateCommissionRequest
    ): CalculateCommissionResponse = hermesService.process(request)

}