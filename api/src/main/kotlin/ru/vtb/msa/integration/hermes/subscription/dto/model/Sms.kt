package ru.vtb.msa.integration.hermes.subscription.dto.model

import java.math.BigInteger

data class Sms(
        val provider: String?,
        val phoneNumber: BigInteger?
)