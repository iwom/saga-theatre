package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Reservations {
  @JsonProperty("data") private List<Reservation> data;
  @JsonProperty("total") private Integer total;

  public List<Reservation> getData() {
    return data;
  }

  public void setData(List<Reservation> data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}
