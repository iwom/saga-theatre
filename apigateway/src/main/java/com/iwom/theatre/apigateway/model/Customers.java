package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Customers {
  @JsonProperty private List<Customer> customers;

  public Customers(List<Customer> customers) {
    this.customers = customers;
  }

  public List<Customer> getCustomers() {
    return customers;
  }
}
