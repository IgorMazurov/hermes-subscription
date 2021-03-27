package ru.vtb.msa.integration.hermes.subscription.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.web.client.RestTemplate
import ru.vtb.msa.integration.hermes.subscription.properties.IntegrationHermesProperties
import ru.vtb.msa.integration.hermes.subscription.service.IntegrationHermesAdapter
import ru.vtb.msa.integration.hermes.subscription.service.impl.IntegrationHermesAdapterImpl
import ru.vtb.msa.integration.hermes.subscription.service.impl.IntegrationHermesAdapterMock

@Configuration
class HermesServiceConfig {

    @Bean
    @Primary
    @ConditionalOnProperty("integration.hermes.mock-enabled", havingValue = "true", matchIfMissing = false)
    fun hermesSubscriptionAdapterMock(
            jaxb2Marshaller: Jaxb2Marshaller
    ): IntegrationHermesAdapter = IntegrationHermesAdapterMock(jaxb2Marshaller)

    @Bean
    @ConditionalOnMissingBean
    fun hermesSubscriptionAdapter(
            template: RestTemplate,
            props: IntegrationHermesProperties
    ): IntegrationHermesAdapter = IntegrationHermesAdapterImpl(template, props)
}