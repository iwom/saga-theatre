package com.iwom.theatre.reservation.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Movies(
  @JsonProperty("data") val data: List<Movie>,
  @JsonProperty("total") val total: Int,
)


data class Movie(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("year") val year: Int,
  @JsonProperty("price") val price: Int,
  @JsonProperty("seats") var seats: Int
)