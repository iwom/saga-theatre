package com.iwom.theatre.reservation.event

import com.fasterxml.jackson.annotation.JsonProperty

sealed class PaymentEvent(
  open val id: Int
)

data class PaymentFailedEvent(
  @JsonProperty("id") override val id: Int,
  @JsonProperty("userId") val userId: Int
) : PaymentEvent(id)

data class PaymentSucceededEvent (
  @JsonProperty("id") override val id: Int,
  @JsonProperty("userId") val userId: Int
) : PaymentEvent(id)