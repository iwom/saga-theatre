package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
  @JsonProperty private Integer id;
  @JsonProperty private String cardNo;

  public Customer(Integer id, String cardNo) {
    this.id = id;
    this.cardNo = cardNo;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }
}
