package com.iwom.theatre.apigateway.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PaymentClientConfig {

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.iwom.theatre.apigateway.payment.model");
    return marshaller;
  }

  @Bean
  public PaymentClient countryClient(Jaxb2Marshaller marshaller) {
    PaymentClient client = new PaymentClient();
    client.setDefaultUri("http://localhost:8010/soap-api/service/payment");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}