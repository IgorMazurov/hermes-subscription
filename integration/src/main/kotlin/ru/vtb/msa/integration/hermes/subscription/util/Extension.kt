package ru.vtb.msa.integration.hermes.subscription.util

import io.micrometer.core.instrument.util.IOUtils
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import java.io.StringReader
import kotlin.reflect.KClass


fun Throwable.findCause() = this.cause ?: this

inline fun <reified T : Any> String.convertToObject(jaxb2Marshaller: Jaxb2Marshaller): T {
    val jaxbUnmarshaller = jaxb2Marshaller.createUnmarshaller()
    return jaxbUnmarshaller.unmarshal(StringReader(this)) as T
}

fun <T : Any> KClass<T>.loadResource(path: String): String =
        this::class.java.classLoader.getResourceAsStream(path).use { IOUtils.toString(it) }