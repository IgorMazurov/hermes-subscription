package ru.vtb.msa.integration.hermes.subscription.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import ru.vtb24.services.hermes.v_1.ObjectFactory

@Configuration
class JaxbConfig {

    @Bean
    fun jaxb2Marshaller(): Jaxb2Marshaller {
        val contextPaths = listOf(ObjectFactory::class.java)
                .map { it.getPackage() }
                .map { it.name }
                .toTypedArray()

        val marshaller = Jaxb2Marshaller()
        marshaller.setContextPaths(*contextPaths)
        marshaller.setSupportJaxbElementClass(true)
        return marshaller
    }
}