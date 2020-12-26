package com.iwom.theatre.apigateway.payment;

import com.iwom.theatre.apigateway.payment.model.FetchPayments;
import com.iwom.theatre.apigateway.payment.model.FetchPaymentsResponse;
import com.iwom.theatre.apigateway.payment.model.PaymentDetails;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;

public class PaymentClient extends WebServiceGatewaySupport {
  private final String TYPE = "USER_PAYMENT";

  public FetchPaymentsResponse fetchPayments() {
    FetchPayments request = new FetchPayments();
    request.setArg0(TYPE);
    FetchPaymentsResponse response =  ((JAXBElement<FetchPaymentsResponse>) getWebServiceTemplate().marshalSendAndReceive(request)).getValue();
    return response;
  }
}