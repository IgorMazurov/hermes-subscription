package ru.vtb.msa.integration.hermes.subscription.mapper.response

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.config.INNER_HERMES_ERROR_CODE
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetActiveSmsNotificationPacketResponse
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb24.services.hermes.v_1.NotificationService
import java.math.BigInteger


@Component
class GetActiveSmsNotificationPacketResponseMapper : ResponseMapper<NotificationService, GetActiveSmsNotificationPacketResponse> {

    override val operation: HermesOperation
        get() = HermesOperation.GET_ACTIVE_SMS_NOTIFICATION_PACKET

    override fun processResponse(result: NotificationService): GetActiveSmsNotificationPacketResponse {
        val exception: HermesException? = result.subscriptionManager?.getNotificationObjects?.response?.exceptions
                ?.hermesException
                ?.description
                ?.let { HermesException(INNER_HERMES_ERROR_CODE, it) }

        val validContacts = result.subscriptionManager?.getNotificationObjects?.response?.client
                ?.contacts
                ?.contact
                ?.filter { it.channel == "sms" }
                ?.map { it.id }
                .orEmpty()

        val activePacket = result.subscriptionManager?.getNotificationObjects?.response?.client
                ?.notificationPackets
                ?.notificationPacket
                ?.firstOrNull { packet ->
                    packet.enabled == BigInteger.ONE &&
                            packet.subscriptions.subscription.any { it.isLock == BigInteger.ZERO && it.contact.id in validContacts }
                }
                ?.code

        return GetActiveSmsNotificationPacketResponse(activePacket, exception)
    }

}