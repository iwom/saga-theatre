package com.iwom.theatre.reservation

import com.iwom.theatre.reservation.event.PaymentEvent
import com.iwom.theatre.reservation.event.PaymentFailedEvent
import com.iwom.theatre.reservation.event.PaymentSucceededEvent
import com.iwom.theatre.reservation.event.ReservationPendingEvent
import com.iwom.theatre.reservation.model.Reservation
import com.iwom.theatre.reservation.request.CreateReservationRequest
import com.iwom.theatre.reservation.service.MovieService
import com.iwom.theatre.reservation.service.ReservationService
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
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
  lateinit var reservationService: ReservationService

  @Autowired
  lateinit var movieService: MovieService

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

    rest("/api/")
      .description("Reservation Service")
      .id("api-route")
      .post("/reservations")
      .produces(MediaType.APPLICATION_JSON)
      .consumes(MediaType.APPLICATION_JSON)
      .bindingMode(RestBindingMode.auto)
      .type(CreateReservationRequest::class.java)
      .enableCORS(true)
      .to("direct:createReservation")
      .get("/reservations")
      .produces(MediaType.APPLICATION_JSON)
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getReservations")
      .get("/movies")
      .produces(MediaType.APPLICATION_JSON)
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getMovies")


    from("direct:createReservation")
      .routeId("createReservation")
      .onCompletion().onCompleteOnly().to("direct:startReservationSaga").end()
      .process {
        val request = it.message.body as CreateReservationRequest
        val movie = movieService.getById(request.movieId)
        val reservation = reservationService.create(movie, request.userId, request.seats)
        it.message.body = reservation
      }
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))

    from("direct:getReservations")
      .routeId("getReservations")
      .process {
        it.message.body = reservationService.fetch()
      }
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))

    from("direct:getMovies")
      .routeId("getMovies")
      .process {
        it.message.body = movieService.fetch()
      }
      .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))

    from("direct:startReservationSaga")
      .routeId("startReservationSaga")
      .unmarshal()
      .json(JsonLibrary.Jackson, Reservation::class.java)
      .process {
        val reservation = it.message.body as Reservation
        it.message.body = ReservationPendingEvent(
          id = reservation.id,
          userId = reservation.userId
        )
      }
      .marshal()
      .json()
      .to("kafka:reservation_events?brokers=localhost:9092")

    from("kafka:payment_succeeded_events?brokers=localhost:9092")
      .routeId("paymentSucceeded")
      .unmarshal()
      .json(JsonLibrary.Jackson, PaymentSucceededEvent::class.java)
      .to("direct:finishReservationSaga")

    from("kafka:payment_failed_events?brokers=localhost:9092")
      .routeId("paymentFailed")
      .unmarshal()
      .json(JsonLibrary.Jackson, PaymentFailedEvent::class.java)
      .to("direct:finishReservationSaga")

    from("direct:finishReservationSaga")
      .routeId("finishReservationSaga")
      .process {
        val event = it.message.body as PaymentEvent
        reservationService.updateStatus(event)
      }
  }
}