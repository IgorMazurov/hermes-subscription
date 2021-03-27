package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class Contact(
        val address: Address?,
        val id: String?,
        val name: String?,
        val channel: String?,
        val isEnabled: BigInteger?
)