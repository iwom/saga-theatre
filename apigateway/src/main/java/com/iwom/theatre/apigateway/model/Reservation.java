package com.iwom.theatre.apigateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservation {
  @JsonProperty("id") private Integer id;
  @JsonProperty("status") private ReservationStatus status;
  @JsonProperty("movieId") private Integer movieId;
  @JsonProperty("userId") private Integer userId;
  @JsonProperty("seats") private Integer seats;
  @JsonProperty("price") private Integer price;

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }

  public Integer getMovieId() {
    return movieId;
  }

  public void setMovieId(Integer movieId) {
    this.movieId = movieId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getSeats() {
    return seats;
  }

  public void setSeats(Integer seats) {
    this.seats = seats;
  }
}
