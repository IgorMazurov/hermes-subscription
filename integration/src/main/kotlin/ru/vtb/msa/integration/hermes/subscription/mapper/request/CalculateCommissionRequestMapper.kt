package ru.vtb.msa.integration.hermes.subscription.mapper.request

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.dto.request.CalculateCommissionRequest
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.util.generateCorrelationId
import ru.vtb24.services.hermes.v_1.NotificationService

@Component
class CalculateCommissionRequestMapper : RequestMapper<CalculateCommissionRequest, NotificationService> {

    override val operation: HermesOperation
        get() = HermesOperation.CALCULATE_COMMISSION

    override fun processRequest(input: CalculateCommissionRequest): NotificationService {
        val req = NotificationService.BillingManager.CalculateCommission.Request().apply {
            client = NotificationService.BillingManager.CalculateCommission.Request.Client().apply {
                systemID = input.client.systemId
                number = input.client.number
                type = input.client.type
                globalID = input.client.globalId.orEmpty()
                notificationPackets = NotificationService.BillingManager.CalculateCommission.Request.Client.NotificationPackets().apply {
                    notificationPacket = input.client.notificationPackets.map { packet ->
                        NotificationService.BillingManager.CalculateCommission.Request.Client.NotificationPackets.NotificationPacket().apply {
                            code = packet
                        }
                    }
                }
            }
            initiatorProcess = NotificationService.BillingManager.CalculateCommission.Request.InitiatorProcess().apply {
                userName = input.initiatorProcess?.userName.orEmpty()
                processID = input.initiatorProcess?.processId.orEmpty()
                systemID = input.initiatorProcess?.systemId.orEmpty()
            }
        }

        return NotificationService().apply {
            billingManager = NotificationService.BillingManager().apply {
                calculateCommission = NotificationService.BillingManager.CalculateCommission().apply {
                    request = req
                    correlationID = generateCorrelationId()
                }
            }
        }
    }

}