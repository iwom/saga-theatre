package com.iwom.theatre.gateway

import com.iwom.theatre.gateway.request.CreateReservationRequest
import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.apache.camel.model.rest.RestBindingMode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Router : RouteBuilder() {
  @Value("\${server.port}")
  lateinit var serverPort: String

  @Value("\${gateway.api.v1}")
  lateinit var contextPath: String


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
      .produces("application/json")
      .consumes("application/json")
      .bindingMode(RestBindingMode.auto)
      .type(CreateReservationRequest::class.java)
      .enableCORS(true)
      .to("direct:createReservation")
      .get("/reservations")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getReservations")
      .get("/movies")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getMovies")

    from("direct:createReservation")
      .marshal().json(JsonLibrary.Jackson)
      .setHeader(Exchange.HTTP_METHOD, constant("POST"))
      .recipientList(simple("http://localhost:8000/camel/api/reservations?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson)

    from("direct:getReservations")
      .marshal().json(JsonLibrary.Jackson)
      .recipientList(simple("http://localhost:8000/camel/api/reservations?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson)

    from("direct:getMovies")
      .marshal().json(JsonLibrary.Jackson)
      .recipientList(simple("http://localhost:8000/camel/api/movies?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson)
  }
}