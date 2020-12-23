package com.iwom.theatre.reservation.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateReservationRequest(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String
)