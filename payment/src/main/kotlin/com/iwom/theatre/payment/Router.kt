package com.iwom.theatre.payment

import com.iwom.theatre.payment.event.ReservationPendingEvent
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.springframework.stereotype.Component


@Component
class Router : RouteBuilder() {
  override fun configure() {
    from("kafka:reservation_events?brokers=localhost:9092")
      .routeId("processReservationCreated")
      .unmarshal()
      .json(JsonLibrary.Jackson, ReservationPendingEvent::class.java)
      .process {
        val event = it.message.body
        println(event::class.java)
        println(event)
      }
  }
}