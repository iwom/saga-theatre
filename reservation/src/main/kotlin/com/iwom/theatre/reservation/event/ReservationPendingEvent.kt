package com.iwom.theatre.reservation.event

import com.fasterxml.jackson.annotation.JsonProperty

sealed class ReservationEvent

data class ReservationPendingEvent(
  @JsonProperty("id") val id: Int,
  @JsonProperty("userId") val userId: Int,
  @JsonProperty("price") val price: Int
) : ReservationEvent()