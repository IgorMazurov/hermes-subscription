package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class AllowEvent(
        val type: String?,
        val visible: BigInteger?,
        val group: BigInteger?
)