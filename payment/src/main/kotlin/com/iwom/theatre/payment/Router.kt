package com.iwom.theatre.payment

import com.iwom.theatre.payment.event.PaymentFailedEvent
import com.iwom.theatre.payment.event.PaymentSucceededEvent
import com.iwom.theatre.payment.event.ReservationPendingEvent
import com.iwom.theatre.payment.service.PaymentService
import com.iwom.theatre.payment.service.PaymentValidationException
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class Router : RouteBuilder() {

  @Autowired
  lateinit var service: PaymentService

  override fun configure() {
    onException(PaymentValidationException::class.java)
      .handled(true)
      .process {
        val event = it.message.body as ReservationPendingEvent
        it.message.body = PaymentFailedEvent(
          id = event.id,
          userId = event.userId
        )
      }
      .marshal()
      .json()
      .to("kafka:payment_failed_events?brokers=localhost:9092")

    from("kafka:reservation_events?brokers=localhost:9092")
      .routeId("processReservationCreated")
      .tracing()
      .threads(10)
      .delay(5000)
      .unmarshal()
      .json(JsonLibrary.Jackson, ReservationPendingEvent::class.java)
      .process {
        val event = it.message.body as ReservationPendingEvent
        service.validatePayment(event.userId)
        it.message.body = PaymentSucceededEvent(
          id = event.id,
          userId = event.userId
        )
      }
      .marshal()
      .json()
      .to("kafka:payment_succeeded_events?brokers=localhost:9092")
  }
}