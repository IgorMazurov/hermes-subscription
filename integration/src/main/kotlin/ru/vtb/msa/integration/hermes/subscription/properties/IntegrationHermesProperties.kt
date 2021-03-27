package ru.vtb.msa.integration.hermes.subscription.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "integration.hermes")
data class IntegrationHermesProperties(
        val soapConnectionTimeout: Duration,
        val soapReadTimeout: Duration,
        val hermesNotificationUrl: String,
        val subscriptionLocalPart: String,
        val calculationLocalPart: String,
        val mockEnabled: Boolean
)