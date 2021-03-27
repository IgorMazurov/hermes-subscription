package ru.vtb.msa.integration.hermes.subscription.service.impl

import org.springframework.oxm.jaxb.Jaxb2Marshaller
import ru.vtb.msa.integration.hermes.subscription.exception.HermesNotAnswerException
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation.*
import ru.vtb.msa.integration.hermes.subscription.service.IntegrationHermesAdapter
import ru.vtb.msa.integration.hermes.subscription.util.convertToObject
import ru.vtb.msa.integration.hermes.subscription.util.loadResource
import ru.vtb24.services.hermes.v_1.NotificationService

class IntegrationHermesAdapterMock(
        private val jaxb2Marshaller: Jaxb2Marshaller
) : IntegrationHermesAdapter {

    private val responses: Map<HermesOperation, Any> by lazy { initMap() }

    private fun initMap(): Map<HermesOperation, Any> =
            mapOf(
                    CALCULATE_COMMISSION to calculationCommission(),
                    GET_NOTIFICATION_OBJECTS to getNotificationObjects(),
                    GET_ACTIVE_SMS_NOTIFICATION_PACKET to getActiveSmsNotificationPacket()
            )

    override fun <T> execute(request: T, operation: HermesOperation): Any = responses[operation]
            ?: throw HermesNotAnswerException()

    private fun getNotificationObjects() = this::class.loadResource("mock/notification-objects-response.xml")
            .convertToObject<NotificationService>(jaxb2Marshaller)

    private fun getActiveSmsNotificationPacket() = this::class.loadResource("mock/active-sms-notification-packet-response.xml")
            .convertToObject<NotificationService>(jaxb2Marshaller)

    private fun calculationCommission() = this::class.loadResource("mock/calculation-commission-response.xml")
            .convertToObject<NotificationService>(jaxb2Marshaller)
}