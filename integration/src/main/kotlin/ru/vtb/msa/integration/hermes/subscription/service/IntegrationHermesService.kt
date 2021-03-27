package ru.vtb.msa.integration.hermes.subscription.service

import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service
import ru.sokomishalov.commons.core.log.Loggable
import ru.vtb.msa.integration.hermes.subscription.dto.request.CalculateCommissionRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetActiveSmsNotificationPacketRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetNotificationObjectsRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.HermesRequest
import ru.vtb.msa.integration.hermes.subscription.mapper.request.RequestMapper
import ru.vtb.msa.integration.hermes.subscription.mapper.response.ResponseMapper
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import javax.annotation.PostConstruct


@Service
@Suppress("unchecked_cast")
class IntegrationHermesService(
        private val integrationHermesAdapter: IntegrationHermesAdapter,
        private val requestMappers: ObjectProvider<RequestMapper<*, *>>,
        private val responseMappers: ObjectProvider<ResponseMapper<*, *>>
) {

    private val requestTypeMap: MutableMap<HermesOperation, RequestMapper<Any, *>> = mutableMapOf()
    private val responseTypeMap: MutableMap<HermesOperation, ResponseMapper<Any, *>> = mutableMapOf()

    @PostConstruct
    fun init() {
        requestMappers.forEach { requestTypeMap[it.operation] = it as RequestMapper<Any, *> }
        responseMappers.forEach { responseTypeMap[it.operation] = it as ResponseMapper<Any, *> }
    }

    fun <T : HermesRequest, R> process(request: T): R {
        val operation = searchOperation(request)
        val reqMapper = requestTypeMap[operation]
        requireNotNull(reqMapper) { "Can't find request mapper for operation: $operation" }
        val req = reqMapper.processRequest(request)
        val response = integrationHermesAdapter.execute(req, operation)
        val respMapper = responseTypeMap[operation]
        requireNotNull(respMapper) { "Can't find response mapper for operation: $operation" }
        return respMapper.processResponse(response) as R
    }

    private fun searchOperation(request: HermesRequest): HermesOperation {
        return when (request) {
            is CalculateCommissionRequest -> HermesOperation.CALCULATE_COMMISSION
            is GetNotificationObjectsRequest -> HermesOperation.GET_NOTIFICATION_OBJECTS
            is GetActiveSmsNotificationPacketRequest -> HermesOperation.GET_ACTIVE_SMS_NOTIFICATION_PACKET
        }
    }

    companion object : Loggable

}