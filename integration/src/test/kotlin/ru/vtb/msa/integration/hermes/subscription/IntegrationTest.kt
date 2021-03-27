package ru.vtb.msa.integration.hermes.subscription

import com.fasterxml.jackson.module.kotlin.readValue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.slf4j.MDC
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import ru.sokomishalov.commons.core.serialization.OBJECT_MAPPER
import ru.vtb.msa.core.protocols.HttpAddressingProtocol
import ru.vtb.msa.integration.hermes.subscription.dto.request.CalculateCommissionRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetActiveSmsNotificationPacketRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetNotificationObjectsRequest
import ru.vtb.msa.integration.hermes.subscription.dto.response.CalculateCommissionResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetActiveSmsNotificationPacketResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetNotificationObjectsResponse
import ru.vtb.msa.integration.hermes.subscription.mapper.request.RequestMapper
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation.*
import ru.vtb.msa.integration.hermes.subscription.util.convertToObject
import ru.vtb.msa.integration.hermes.subscription.util.loadResource
import ru.vtb.msa.integration.hermes.subscription.utils.CALCULATE_COMMISSION_URL
import ru.vtb.msa.integration.hermes.subscription.utils.GET_ACTIVE_SMS_NOTIFICATION_PACKET_URL
import ru.vtb.msa.integration.hermes.subscription.utils.GET_NOTIFICATION_OBJECTS_URL
import ru.vtb24.services.hermes.v_1.NotificationService

@RunWith(SpringRunner::class)
@Suppress("unchecked_cast")
@ActiveProfiles(profiles = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @Autowired
    private lateinit var requestMappers: ObjectProvider<RequestMapper<*, *>>

    @LocalServerPort
    private val port = 0

    @MockBean
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var jaxb2Marshaller: Jaxb2Marshaller

    @Before
    fun setUp() {
        RestAssured.port = port
        requestMappers.forEach { requestTypeMap[it.operation] = it as RequestMapper<Any, *> }
    }

    @Test
    fun `get active sms notification packet test`() {
        val hermesResponse = this::class.loadResource("response/active-sms-notification-packet-response.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val expectedResponse = this::class.loadResource("response/rest/active-sms-notification-packet-response.json")
        val response = OBJECT_MAPPER.readValue<GetActiveSmsNotificationPacketResponse>(expectedResponse)
        val request = this::class.loadResource("request/active-sms-notification-packet-request.json")
        whenever(restTemplate.exchange(any<String>(), eq(HttpMethod.POST), any(), eq(NotificationService::class.java)))
                .thenReturn(ResponseEntity.ok(hermesResponse))
        val httpResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.readValue<GetActiveSmsNotificationPacketRequest>(request))
                .`when`()
                .post(GET_ACTIVE_SMS_NOTIFICATION_PACKET_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asByteArray()

        assertEquals(response, OBJECT_MAPPER.readValue<GetActiveSmsNotificationPacketResponse>(httpResponse))
    }

    @Test
    fun `get notification objects test`() {
        val hermesResponse = this::class.loadResource("response/notification-objects-response.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val expectedResponse = this::class.loadResource("response/rest/notification-objects-response.json")
        val response = OBJECT_MAPPER.readValue<GetNotificationObjectsResponse>(expectedResponse)
        val request = this::class.loadResource("request/notification-objects-request.json")
        whenever(restTemplate.exchange(any<String>(), eq(HttpMethod.POST), any(), eq(NotificationService::class.java)))
                .thenReturn(ResponseEntity.ok(hermesResponse))
        val httpResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.readValue<GetNotificationObjectsRequest>(request))
                .`when`()
                .post(GET_NOTIFICATION_OBJECTS_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asByteArray()

        assertEquals(response, OBJECT_MAPPER.readValue<GetNotificationObjectsResponse>(httpResponse))
    }

    @Test
    fun `calculate commission test`() {
        val hermesResponse = this::class.loadResource("response/calculation-commission-response.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val expectedResponse = this::class.loadResource("response/rest/calculation-commission-response.json")
        val response = OBJECT_MAPPER.readValue<CalculateCommissionResponse>(expectedResponse)
        val request = this::class.loadResource("request/calculation-commission-request.json")
        whenever(restTemplate.exchange(any<String>(), eq(HttpMethod.POST), any(), eq(NotificationService::class.java)))
                .thenReturn(ResponseEntity.ok(hermesResponse))
        val httpResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(OBJECT_MAPPER.readValue<CalculateCommissionRequest>(request))
                .`when`()
                .post(CALCULATE_COMMISSION_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .asByteArray()

        assertEquals(response, OBJECT_MAPPER.readValue<CalculateCommissionResponse>(httpResponse))
    }

    @Test
    fun `get active sms notification packet mapper test`() {
        MDC.put(HttpAddressingProtocol.MESSAGE_ID, "1")
        val message = this::class.loadResource("request/active-sms-notification-packet-request.json")
        val request = OBJECT_MAPPER.readValue<GetActiveSmsNotificationPacketRequest>(message)
        val result = this::class.loadResource("mapped/mapped-active-sms-notification-packet-request.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val mapped = requestTypeMap[GET_ACTIVE_SMS_NOTIFICATION_PACKET]?.processRequest(request)
        assertEquals(result, mapped)
    }

    @Test
    fun `get notification objects mapper test`() {
        MDC.put(HttpAddressingProtocol.MESSAGE_ID, "1")
        val message = this::class.loadResource("request/notification-objects-request.json")
        val request = OBJECT_MAPPER.readValue<GetNotificationObjectsRequest>(message)
        val result = this::class.loadResource("mapped/mapped-notification-objects-request.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val mapped = requestTypeMap[GET_NOTIFICATION_OBJECTS]?.processRequest(request)
        assertEquals(result, mapped)
    }

    @Test
    fun `calculate commission mapper test`() {
        MDC.put(HttpAddressingProtocol.MESSAGE_ID, "1")
        val message = this::class.loadResource("request/calculation-commission-request.json")
        val request = OBJECT_MAPPER.readValue<CalculateCommissionRequest>(message)
        val result = this::class.loadResource("mapped/mapped-calculation-commission-request.xml")
                .convertToObject<NotificationService>(jaxb2Marshaller)
        val mapped = requestTypeMap[CALCULATE_COMMISSION]?.processRequest(request)
        assertEquals(result, mapped)
    }

    companion object {
        private val requestTypeMap: MutableMap<HermesOperation, RequestMapper<Any, *>> = mutableMapOf()
    }

}