package com.iwom.theatre.apigateway;

import com.iwom.theatre.apigateway.model.CreateReservationRequest;
import com.iwom.theatre.apigateway.model.Movies;
import com.iwom.theatre.apigateway.model.PaymentRequestBuilder;
import com.iwom.theatre.apigateway.model.Reservation;
import com.iwom.theatre.apigateway.model.Reservations;
import com.iwom.theatre.payment.service.FetchPayments;
import com.iwom.theatre.payment.service.PaymentService;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Router extends RouteBuilder {

  @Value("${server.port}")
  private Integer serverPort;

  @Value("${gateway.api.v1}")
  private String contextPath;

  @Override
  public void configure() throws Exception {

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
      .get("/payments")
      .produces("application/json")
      .bindingMode(RestBindingMode.auto)
      .enableCORS(true)
      .to("direct:getPayments");

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

    from("timer://my-timer?fixedRate=true&period=1000")
      .tracing()

      .setHeader(CxfConstants.OPERATION_NAME,
        constant("fetchPayments"))
      .setHeader(CxfConstants.OPERATION_NAMESPACE,
        constant("http://service.payment.theatre.iwom.com/"))

      // Invoke our test service using CXF
      .to("cxf://http://localhost:8010/soap-api/service/payment?"
        + "?serviceClass=com.iwom.theatre.payment.service.PaymentService"
        + "&wsdlURL=/wsdl/payment.wsdl");
  }
}
