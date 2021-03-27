package ru.vtb.msa.integration.hermes.subscription.mapper.response

import org.springframework.stereotype.Component
import ru.vtb.msa.integration.hermes.subscription.config.INNER_HERMES_ERROR_CODE
import ru.vtb.msa.integration.hermes.subscription.dto.model.*
import ru.vtb.msa.integration.hermes.subscription.dto.response.GetNotificationObjectsResponse
import ru.vtb.msa.integration.hermes.subscription.model.HermesOperation
import ru.vtb24.services.hermes.v_1.NotificationService


@Component
class GetNotificationObjectsResponseMapper : ResponseMapper<NotificationService, GetNotificationObjectsResponse> {

    override val operation: HermesOperation
        get() = HermesOperation.GET_NOTIFICATION_OBJECTS

    override fun processResponse(result: NotificationService): GetNotificationObjectsResponse {
        val exception: HermesException? = result.subscriptionManager?.getNotificationObjects?.response?.exceptions
                ?.hermesException
                ?.description
                ?.let { HermesException(INNER_HERMES_ERROR_CODE, it) }

        val client =
                result.subscriptionManager?.getNotificationObjects?.response
                        ?.client
                        ?.let {
                            ResponseClient(
                                    notificationPackets = it.notificationPackets?.notificationPacket
                                            ?.map { packet ->
                                                NotificationPacket(
                                                        allowEvents = packet.allowEvents?.event
                                                                ?.map { event ->
                                                                    AllowEvent(
                                                                            type = event.type,
                                                                            visible = event.visible,
                                                                            group = event.group
                                                                    )
                                                                },
                                                        subscriptions = packet.subscriptions?.subscription
                                                                ?.map { subscription ->
                                                                    Subscription(
                                                                            contact = subscription.contact?.id,
                                                                            deliveredSchema = subscription.deliveredSchema?.id,
                                                                            events = subscription.events?.event?.map { event -> event.type },
                                                                            id = subscription.id,
                                                                            name = subscription.name,
                                                                            isLock = subscription.isLock,
                                                                            doRemovePrivateInfo = subscription.doRemovePrivateInfo,
                                                                            encoding = subscription.encoding,
                                                                            contentType = subscription.contentType,
                                                                            eventPacket = subscription.eventPacket
                                                                    )
                                                                },
                                                        code = packet.code,
                                                        enabled = packet.enabled,
                                                        autoFee = packet.autoFee
                                                )
                                            },
                                    contacts = it.contacts?.contact?.map { contact ->
                                        Contact(
                                                address = contact.address?.let { address ->
                                                    Address(
                                                            sms = address.sms?.let { sms -> Sms(sms.provider, sms.phoneNumber) },
                                                            smtp = address.smtp?.let { smtp -> Smtp(smtp.mailto) }
                                                    )
                                                },
                                                id = contact.id,
                                                name = contact.name,
                                                channel = contact.channel,
                                                isEnabled = contact.isEnable
                                        )
                                    },
                                    deliveredSchemas = it.deliveredSchemas?.deliveredSchema?.map { schema ->
                                        DeliveredSchema(
                                                scheduling = schema.scheduling?.interval
                                                        ?.map { interval ->
                                                            Interval(
                                                                    type = interval.type,
                                                                    begPoint = interval.begPoint,
                                                                    endPoint = interval.endPoint
                                                            )
                                                        }
                                                        ?.let { intervals -> Scheduling(intervals) },
                                                id = schema.id,
                                                name = schema.name,
                                                gmt = schema.gmt,
                                                isDefault = schema.isDefault
                                        )
                                    },
                                    id = it.id,
                                    lastName = it.lastName,
                                    firstName = it.firstName,
                                    middleName = it.middleName,
                                    birthDate = it.birthDate,
                                    account = it.account,
                                    accountSystemId = it.accountSystemID
                            )
                        }

        return GetNotificationObjectsResponse(client, exception)
    }

}