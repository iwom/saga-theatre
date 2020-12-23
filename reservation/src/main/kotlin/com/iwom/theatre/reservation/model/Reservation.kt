package com.iwom.theatre.reservation.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Reservation(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String
)