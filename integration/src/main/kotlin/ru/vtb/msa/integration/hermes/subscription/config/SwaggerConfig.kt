package ru.vtb.msa.integration.hermes.subscription.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import ru.sokomishalov.commons.spring.swagger.customize
import ru.sokomishalov.commons.spring.swagger.redirectRootToSwagger
import ru.vtb.msa.integration.hermes.subscription.IntegrationHermesApplication
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


@Configuration
class SwaggerConfig {

    @Bean
    fun docket(buildProperties: ObjectProvider<BuildProperties>): Docket {
        return Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(IntegrationHermesApplication::class.java.`package`.name))
                .build()
                .useDefaultResponseMessages(false)
                .customize(
                        contact = Contact("Andrey", null, "avtyapkin@vtb.ru"),
                        title = "Integration Hermes Subscription",
                        description = "This service provides endpoint for Hermes Subscription",
                        buildProperties = buildProperties.ifAvailable
                )
    }

    @Bean
    fun swaggerRedirect(): RouterFunction<ServerResponse> = router { redirectRootToSwagger() }

}