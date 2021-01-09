package com.iwom.theatre.apigateway;

import com.iwom.theatre.apigateway.model.CreateReservationRequest;
import com.iwom.theatre.apigateway.model.Movies;
import com.iwom.theatre.apigateway.model.Reservation;
import com.iwom.theatre.apigateway.model.Reservations;
import com.iwom.theatre.apigateway.payment.PaymentProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Router extends RouteBuilder {

  @Value("${server.port}")
  private Integer serverPort;

  @Value("${gateway.api.v1}")
  private String contextPath;

  @Autowired
  private PaymentProcessor paymentProcessor;

  @Override
  public void configure() throws Exception {

    restConfiguration()
      .contextPath(contextPath)
      .port(serverPort)
      .enableCORS(true)
      .apiContextPath("/api-doc")
      .apiProperty("api.title", "Api gateway REST API")
      .apiProperty("api.version", "v1")
      .apiProperty("cors", "true")
      .apiContextRouteId("doc-api")
      .component("servlet")
      .bindingMode(RestBindingMode.json)
      .dataFormatProperty("prettyPrint", "true");

    rest("/api/")
      .description("Reservation Service")
      .id("api-route")
      .post("/reservations")
      .produces("application/json")
      .consumes("application/json")
      .bindingMode(RestBindingMode.auto)
      .type(CreateReservationRequest.class)
      .outType(Reservation.class)
      .enableCORS(true)
      .to("direct:createReservation")
      .get("/reservations")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .outType(Reservations.class)
      .enableCORS(true)
      .to("direct:getReservations")
      .get("/movies")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .outType(Movies.class)
      .enableCORS(true)
      .to("direct:getMovies")
      .get("/customers")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getCustomers");

    from("direct:createReservation")
      .marshal().json(JsonLibrary.Jackson)
      .setHeader(Exchange.HTTP_METHOD, constant("POST"))
      .recipientList(simple("http://localhost:8000/camel/api/reservations?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson);

    from("direct:getReservations")
      .marshal().json(JsonLibrary.Jackson)
      .recipientList(simple("http://localhost:8000/camel/api/reservations?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson);

    from("direct:getMovies")
      .marshal().json(JsonLibrary.Jackson)
      .recipientList(simple("http://localhost:8000/camel/api/movies?bridgeEndpoint=true"))
      .unmarshal().json(JsonLibrary.Jackson);

    from("direct:getCustomers")
      .process(paymentProcessor);
  }
}
