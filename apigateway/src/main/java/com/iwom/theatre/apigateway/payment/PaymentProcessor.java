package com.iwom.theatre.apigateway.payment;

import com.iwom.theatre.apigateway.model.Customers;
import com.iwom.theatre.apigateway.payment.model.FetchPaymentsResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor implements Processor {
  private final PaymentClient paymentClient;
  private final PaymentMapper paymentMapper;

  @Autowired
  public PaymentProcessor(PaymentClient paymentClient, PaymentMapper paymentMapper) {
    this.paymentClient = paymentClient;
    this.paymentMapper = paymentMapper;
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    FetchPaymentsResponse response = paymentClient.fetchPayments();
    Customers customers = paymentMapper.fromPaymentResponse(response);
    exchange.getIn().setBody(customers);
  }
}
