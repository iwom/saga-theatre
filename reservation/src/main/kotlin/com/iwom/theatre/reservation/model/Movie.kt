package com.iwom.theatre.reservation.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Movie(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("year") val year: Int,
  @JsonProperty("maxSeats") val maxSeats: Int
)