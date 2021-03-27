package ru.vtb.msa.integration.hermes.subscription.mapper.request

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetNotificationObjectsRequest
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.util.generateCorrelationId
import ru.vtb24.services.hermes.v_1.NotificationService


@Component
class GetNotificationObjectsRequestMapper : RequestMapper<GetNotificationObjectsRequest, NotificationService> {

    override val operation: HermesOperation
        get() = HermesOperation.GET_NOTIFICATION_OBJECTS

    override fun processRequest(
            input: GetNotificationObjectsRequest
    ): NotificationService {
        val req = NotificationService.SubscriptionManager.GetNotificationObjects.Request()
        val currentClient = NotificationService.SubscriptionManager.GetNotificationObjects.Request.Client()
        val currentInitiatorProcess = NotificationService.SubscriptionManager.GetNotificationObjects.Request.InitiatorProcess()

        req.apply {
            client = currentClient.apply {
                systemID = input.client.systemId
                number = input.client.number
                type = input.client.type
                globalID = input.client.globalId.orEmpty()
            }
            initiatorProcess = currentInitiatorProcess.apply {
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