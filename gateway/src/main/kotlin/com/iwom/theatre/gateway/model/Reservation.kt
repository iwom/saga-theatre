package com.iwom.theatre.gateway.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Reservations(
  @JsonProperty("data") val data: List<Reservation>,
  @JsonProperty("total") val total: Int,
)

data class Reservation(
  @JsonProperty("id") val id: Int,
  @JsonProperty("status") val status: ReservationStatus,
  @JsonProperty("movieId") val movieId: Int,
  @JsonProperty("userId") val userId: Int,
  @JsonProperty("seats") val seats: Int
)

enum class ReservationStatus(val value: Int) {
  PENDING(1),
  CONFIRMED(2),
  DENIED(-1);

  companion object {
    fun completable() = listOf(PENDING, CONFIRMED)
    val BY_VALUE = values().associateBy { it.value }
  }
}