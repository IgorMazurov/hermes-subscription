package ru.vtb.msa.integration.hermes.subscription.mapper.request

import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation

interface RequestMapper<T, R> {
    val operation: HermesOperation
    fun processRequest(input: T): R
}