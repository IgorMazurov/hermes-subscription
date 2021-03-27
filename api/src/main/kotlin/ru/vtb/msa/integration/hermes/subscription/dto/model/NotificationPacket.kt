package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class NotificationPacket(
        val allowEvents: List<AllowEvent>?,
        val subscriptions: List<Subscription>?,
        val code: String?,
        val enabled: BigInteger?,
        val autoFee: BigInteger?
)