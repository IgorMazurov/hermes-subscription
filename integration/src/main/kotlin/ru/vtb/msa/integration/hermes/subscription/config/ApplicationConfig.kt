package ru.vtb.msa.integration.hermes.subscription.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.web.client.RestTemplate
import ru.vtb.msa.integration.hermes.subscription.properties.IntegrationHermesProperties
import ru.vtb.msa.mdclogging.client.resttemplate.MdcRestTemplateFactory
import ru.vtb.msa.mdclogging.fluentd.EnableMdcFluentd

@Configuration
@EnableMdcFluentd
@EnableConfigurationProperties(IntegrationHermesProperties::class)
class ApplicationConfig {

    @Bean
    fun restTemplate(
            jaxb2Marshaller: Jaxb2Marshaller,
            props: IntegrationHermesProperties
    ): RestTemplate {
        val (connectionTimeout, readTimeout, url) = props
        return MdcRestTemplateFactory.builder(
                connectionTimeout = connectionTimeout,
                readTimeout = readTimeout
        )
                .messageConverters(MarshallingHttpMessageConverter(jaxb2Marshaller))
                .build()
    }

}