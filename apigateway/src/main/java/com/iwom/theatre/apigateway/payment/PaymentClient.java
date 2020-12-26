package com.iwom.theatre.apigateway.payment;

import com.iwom.theatre.apigateway.payment.model.FetchPayments;
import com.iwom.theatre.apigateway.payment.model.FetchPaymentsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;

public class PaymentClient extends WebServiceGatewaySupport {
  public FetchPaymentsResponse fetchPayments() {
    return ((JAXBElement<FetchPaymentsResponse>) getWebServiceTemplate().marshalSendAndReceive(new FetchPayments())).getValue();
  }
}