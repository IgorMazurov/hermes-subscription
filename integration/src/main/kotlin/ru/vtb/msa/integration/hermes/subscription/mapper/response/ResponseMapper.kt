package ru.vtb.msa.integration.hermes.subscription.mapper.response

import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation

interface ResponseMapper<T, R> {
    val operation: HermesOperation
    fun processResponse(result: T): R
}