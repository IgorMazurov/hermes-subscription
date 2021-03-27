package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class CalculationNotificationPacket(
        val code: String?,
        val autoFeeDate: String?,
        val enableCost: BigInteger?,
        val prolongCost: BigInteger?,
        val enableKBO: String?,
        val prolongKBO: String?
)