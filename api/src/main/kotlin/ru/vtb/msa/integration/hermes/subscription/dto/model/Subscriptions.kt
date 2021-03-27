package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class Subscription(
        val contact: String?,
        val deliveredSchema: String?,
        val events: List<String>?,
        val id: String?,
        val name: String?,
        val isLock: BigInteger?,
        val doRemovePrivateInfo: BigInteger?,
        val encoding: String?,
        val contentType: String?,
        val eventPacket: String?
)