package com.iwom.theatre.apigateway.payment;

import com.iwom.theatre.apigateway.model.Customer;
import com.iwom.theatre.apigateway.model.Customers;
import com.iwom.theatre.apigateway.payment.model.FetchPaymentsResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMapper {
  public Customers fromPaymentResponse(FetchPaymentsResponse paymentsResponse) {
    List<Customer> customerList = new ArrayList<>();
    paymentsResponse.getReturn().forEach(details ->
      customerList.add(new Customer(
        details.getUserId(),
        details.getName(),
        details.getLimit(),
        details.getTotal()
      )));
    return new Customers(customerList);
  }
}
