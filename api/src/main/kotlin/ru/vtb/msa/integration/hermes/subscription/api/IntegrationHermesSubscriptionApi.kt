package ru.vtb.msa.integration.hermes.subscription.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.vtb.msa.integration.hermes.subscription.annotation.ApiErrorResponses
import ru.vtb.msa.integration.hermes.subscription.dto.request.CalculateCommissionRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetActiveSmsNotificationPacketRequest
import ru.vtb.msa.integration.hermes.subscription.dto.request.GetNotificationObjectsRequest
import ru.vtb.msa.integration.hermes.subscription.dto.response.CalculateCommissionResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetActiveSmsNotificationPacketResponse
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetNotificationObjectsResponse
import javax.validation.Valid


@Api(value = "Integration hermes subscription api", description = "API hermes subscription")
interface IntegrationHermesSubscriptionApi {

    @ApiErrorResponses
    @PostMapping("/active-subscription")
    @ApiOperation("Получение notification objects")
    fun getActiveSmsNotificationPacket(
            @RequestBody @Valid request: GetActiveSmsNotificationPacketRequest
    ): GetActiveSmsNotificationPacketResponse

    @ApiErrorResponses
    @PostMapping("/notification-objects")
    @ApiOperation("Получение notification objects", hidden = true)
    fun getNotificationObjects(
            @RequestBody @Valid request: GetNotificationObjectsRequest
    ): GetNotificationObjectsResponse

    @ApiErrorResponses
    @PostMapping("/calculate-commission")
    @ApiOperation("Получения параметров комиссии", hidden = true)
    fun calculateCommission(
            @RequestBody @Valid request: CalculateCommissionRequest
    ): CalculateCommissionResponse

}