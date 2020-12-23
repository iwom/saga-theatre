package com.iwom.theatre.payment

import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.ws.rs.core.MediaType


@Component
class Router : RouteBuilder() {
  @Value("\${server.port}")
  lateinit var serverPort: String

  @Value("\${payment.api.v1}")
  lateinit var contextPath: String

  override fun configure() {

    from("kafka:reservation_events?brokers=localhost:9092")
      .routeId("processReservationCreated")
      .process {
        val event = it.message.body
        println(event::class.java)
        println(event)
      }
  }
}