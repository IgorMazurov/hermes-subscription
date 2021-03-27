package ru.vtb.msa.integration.hermes.subscription

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IntegrationHermesApplication

fun main(args: Array<String>) {
    runApplication<IntegrationHermesApplication>(*args)
}