package com.iwom.theatre.reservation.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateReservationRequest(
  @JsonProperty("movieId") val movieId: Int,
  @JsonProperty("userId") val userId: Int,
  @JsonProperty("seats") val seats: Int
)