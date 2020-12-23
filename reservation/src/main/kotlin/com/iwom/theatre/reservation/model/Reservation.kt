package com.iwom.theatre.reservation.model

data class Reservation(
  val id: Int,
  val name: String,
  val status: ReservationStatus = ReservationStatus.PENDING
)

enum class ReservationStatus(val value: Int) {
  PENDING(1),
  CONFIRMED(2),
  DENIED(-1);

  companion object {
    val BY_VALUE = values().associateBy { it.value }
  }
}