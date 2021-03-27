package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class Interval(
        val type: String?,
        val begPoint: BigInteger?,
        val endPoint: BigInteger?
)