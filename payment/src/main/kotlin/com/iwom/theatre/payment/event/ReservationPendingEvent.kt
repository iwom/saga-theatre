package com.iwom.theatre.payment.event

import com.fasterxml.jackson.annotation.JsonProperty

data class ReservationPendingEvent(
  @JsonProperty("id") val id: Int,
  @JsonProperty("userId") val userId: Int,
  @JsonProperty("price") val price: Int
)