package ru.vtb.msa.integration.hermes.subscription.service

import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation

interface IntegrationHermesAdapter {
    fun <T> execute(request: T, operation: HermesOperation): Any
}