package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Movies {
  @JsonProperty("data") private List<Movie> data;
  @JsonProperty("total") private Integer total;

  public List<Movie> getData() {
    return data;
  }

  public void setData(List<Movie> data) {
    this.data = data;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }
}