package com.iwom.theatre.payment.event

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentSucceededEvent (
  @JsonProperty("id") val id: Int,
  @JsonProperty("userId") val userId: Int
)