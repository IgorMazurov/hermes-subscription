package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class ResponseClient(
        val notificationPackets: List<NotificationPacket>?,
        val contacts: List<Contact>?,
        val deliveredSchemas: List<DeliveredSchema>?,
        val id: String?,
        val lastName: String?,
        val firstName: String?,
        val middleName: String?,
        val birthDate: String?,
        val account: BigInteger?,
        val accountSystemId: String?
)