package com.iwom.theatre.payment.event

import com.fasterxml.jackson.annotation.JsonProperty

data class ReservationPendingEvent(
  @JsonProperty("id") val id: Int,
  @JsonProperty("name") val name: String,
  @JsonProperty("creditCardNo") val creditCardNo: String
)