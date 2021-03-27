package ru.vtb.msa.integration.hermes.subscription.service.impl

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import ru.vtb.msa.integration.hermes.subscription.exception.HermesNotAnswerException
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation.*
import ru.vtb.msa.integration.hermes.subscription.properties.IntegrationHermesProperties
import ru.vtb.msa.integration.hermes.subscription.service.IntegrationHermesAdapter
import ru.vtb24.services.hermes.v_1.NotificationService

class IntegrationHermesAdapterImpl(
        private val template: RestTemplate,
        private val props: IntegrationHermesProperties
) : IntegrationHermesAdapter {

    override fun <T> execute(request: T, operation: HermesOperation): Any {
        return template.exchange(
                props.hermesNotificationUrl + findLocalPart(operation),
                HttpMethod.POST,
                HttpEntity(request),
                NotificationService::class.java
        )
                .body
                ?: throw HermesNotAnswerException()
    }

    private fun findLocalPart(operation: HermesOperation) =
            when (operation) {
                CALCULATE_COMMISSION -> props.calculationLocalPart
                GET_NOTIFICATION_OBJECTS -> props.subscriptionLocalPart
                GET_ACTIVE_SMS_NOTIFICATION_PACKET -> props.subscriptionLocalPart
            }

}