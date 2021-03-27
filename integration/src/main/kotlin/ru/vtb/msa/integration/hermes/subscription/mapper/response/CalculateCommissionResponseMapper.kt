package ru.vtb.msa.integration.hermes.subscription.mapper.response

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.config.INNER_HERMES_ERROR_CODE
import ru.vtb.msa.integration.hermes.subscription.dto.model.CalculationNotificationPacket
import ru.vtb.msa.integration.hermes.subscription.dto.model.HermesException
import ru.vtb.msa.integration.hermes.subscription.dto.response.CalculateCommissionResponse
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb24.services.hermes.v_1.NotificationService


@Component
class CalculateCommissionResponseMapper : ResponseMapper<NotificationService, CalculateCommissionResponse> {

    override val operation: HermesOperation
        get() = HermesOperation.CALCULATE_COMMISSION

    override fun processResponse(result: NotificationService): CalculateCommissionResponse {
        val exception: HermesException? = result.billingManager?.calculateCommission?.response?.exceptions
                ?.hermesException
                ?.description
                ?.let { HermesException(INNER_HERMES_ERROR_CODE, it) }

        val notificationPacket = result.billingManager?.calculateCommission?.response
                ?.notificationPackets
                ?.notificationPacket
                ?.map {
                    CalculationNotificationPacket(
                            code = it.code,
                            autoFeeDate = it.autoFeeDate,
                            enableCost = it.enableCost,
                            prolongCost = it.prolongCost,
                            enableKBO = it.enableKBO,
                            prolongKBO = it.prolongKBO
                    )
                }

        return CalculateCommissionResponse(notificationPacket, exception)
    }
}