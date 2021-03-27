package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class DeliveredSchema(
        val scheduling: Scheduling?,
        val id: String?,
        val name: String?,
        val gmt: BigInteger?,
        val isDefault: BigInteger?
)