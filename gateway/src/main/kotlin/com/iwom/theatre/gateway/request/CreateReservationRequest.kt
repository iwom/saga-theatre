package com.iwom.theatre.gateway.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateReservationRequest(
  @JsonProperty("movieId") val movieId: Int,
  @JsonProperty("userId") val userId: Int,
  @JsonProperty("seats") val seats: Int
)