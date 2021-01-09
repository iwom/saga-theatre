package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
  @JsonProperty private Integer id;
  @JsonProperty private String name;
  @JsonProperty private Integer limit;
  @JsonProperty private Integer total;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Customer(Integer id, String name, Integer limit, Integer total) {
    this.id = id;
    this.name = name;
    this.limit = limit;
    this.total = total;
  }
}
