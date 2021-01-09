package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
  @JsonProperty("id") private Integer id;
  @JsonProperty("name") private String name;
  @JsonProperty("year") private Integer year;
  @JsonProperty("price") private Integer price;
  @JsonProperty("seats") private Integer seats;

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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getSeats() {
    return seats;
  }

  public void setSeats(Integer seats) {
    this.seats = seats;
  }
}
