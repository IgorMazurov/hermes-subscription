package ru.vtb.msa.integration.hermes.subscription.dto.response

import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException

data class HermesErrorResponse(
        val exception: HermesException
)