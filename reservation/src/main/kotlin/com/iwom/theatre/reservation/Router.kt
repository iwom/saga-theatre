package com.iwom.theatre.reservation

import com.iwom.theatre.reservation.event.ReservationPendingEvent
import com.iwom.theatre.reservation.request.CreateReservationRequest
import com.iwom.theatre.reservation.service.ReservationService
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

  @Value("\${reservation.api.v1}")
  lateinit var contextPath: String

  @Autowired
  lateinit var service: ReservationService

  override fun configure() {
    restConfiguration()
      .contextPath(contextPath)
      .port(serverPort)
      .enableCORS(true)
      .apiContextPath("/api-doc")
      .apiProperty("api.title", "Test REST API")
      .apiProperty("api.version", "v1")
      .apiProperty("cors", "true")
      .apiContextRouteId("doc-api")
      .component("servlet")
      .bindingMode(RestBindingMode.json)
      .dataFormatProperty("prettyPrint", "true")

    rest("/api/").description("Reservation Service")
      .id("api-route")
      .post("/reservations")
      .produces(MediaType.APPLICATION_JSON)
      .consumes(MediaType.APPLICATION_JSON)
      .bindingMode(RestBindingMode.auto)
      .type(CreateReservationRequest::class.java)
      .enableCORS(true)
      .to("direct:createReservation")
      .to("direct:startReservationSaga")

    from("direct:createReservation")
      .routeId("createReservation")
      .tracing()
      .process {
        it.message.body = service.example(it.message.getBody(CreateReservationRequest::class.java))
      }
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
      .


    from("direct:startReservationSaga")
      .routeId("startReservationSaga")
      .process {
        val request = it.message.body as CreateReservationRequest
        it.message.body = ReservationPendingEvent(
          id = request.id,
          name = request.name,
          creditCardNo = "ABCD"
        )
      }
      .to("kafka:reservation_events?brokers=localhost:9092")
  }
}