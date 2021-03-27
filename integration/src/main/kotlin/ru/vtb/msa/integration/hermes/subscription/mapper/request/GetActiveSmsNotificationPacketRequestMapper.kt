package ru.vtb.msa.integration.hermes.subscription.mapper.request

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetActiveSmsNotificationPacketRequest
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.util.generateCorrelationId
import ru.vtb24.services.hermes.v_1.NotificationService

@Component
class GetActiveSmsNotificationPacketRequestMapper : RequestMapper<GetActiveSmsNotificationPacketRequest, NotificationService> {

    override val operation: HermesOperation
        get() = HermesOperation.GET_ACTIVE_SMS_NOTIFICATION_PACKET

    override fun processRequest(
            input: GetActiveSmsNotificationPacketRequest
    ): NotificationService {
        val req = NotificationService.SubscriptionManager.GetNotificationObjects.Request().apply {
            client = NotificationService.SubscriptionManager.GetNotificationObjects.Request.Client().apply {
                systemID = input.client.systemId
                number = input.client.number
                type = input.client.type
                globalID = input.client.globalId.orEmpty()
            }
            initiatorProcess = NotificationService.SubscriptionManager.GetNotificationObjects.Request.InitiatorProcess().apply {
                userName = input.initiatorProcess?.userName.orEmpty()
                processID = input.initiatorProcess?.processId.orEmpty()
                systemID = input.initiatorProcess?.systemId.orEmpty()
            }
        }

        return NotificationService().apply {
            subscriptionManager = NotificationService.SubscriptionManager().apply {
                getNotificationObjects = NotificationService.SubscriptionManager.GetNotificationObjects().apply {
                    request = req
                    correlationID = generateCorrelationId()
                }
            }
        }
    }
}