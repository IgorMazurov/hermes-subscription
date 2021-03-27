package ru.vtb.msa.integration.hermes.subscription.util

import org.slf4j.MDC
import ru.sokomishalov.commons.core.string.isNotNullOrBlank
import ru.vtb.msa.core.protocols.HttpAddressingProtocol
import java.util.*

fun generateCorrelationId() = MDC.get(HttpAddressingProtocol.MESSAGE_ID)
        .takeIf { it.isNotNullOrBlank() }
        ?: UUID.randomUUID().toString()