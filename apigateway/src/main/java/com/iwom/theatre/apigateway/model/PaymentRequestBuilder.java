package com.iwom.theatre.apigateway.model;

import com.iwom.theatre.payment.service.FetchPayments;

public class PaymentRequestBuilder {
  public FetchPayments fetchPayments(Object param) {
    System.out.println(param.toString());
    return new FetchPayments();
  }
}
