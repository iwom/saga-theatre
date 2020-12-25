package com.iwom.theatre.reservation.service

import com.iwom.theatre.reservation.event.PaymentEvent
import com.iwom.theatre.reservation.event.PaymentFailedEvent
import com.iwom.theatre.reservation.event.PaymentSucceededEvent
import com.iwom.theatre.reservation.model.Movie
import com.iwom.theatre.reservation.model.Reservation
import com.iwom.theatre.reservation.model.ReservationStatus
import com.iwom.theatre.reservation.model.Reservations
import org.springframework.stereotype.Service

@Service
class ReservationService {
  private final var reservations = mutableListOf<Reservation>()

  private fun confirmedReservationsByMovieId() = reservations
    .filter { it.status in ReservationStatus.completable() }
    .groupBy { it.movieId }

  private fun reservationsById() = reservations.associateBy { it.id }

  fun fetch() = Reservations(data = reservations.toList(), total = reservations.size)

  fun create(movie: Movie, userId: Int, seats: Int): Reservation {
    val availableSeats: Int = movie.maxSeats - (confirmedReservationsByMovieId()[movie.id]?.sumBy { it.seats } ?: 0)
    if (availableSeats < seats) {
      throw ReservationValidationException()
    }
    val lastIndex = reservations.maxByOrNull { it.id }?.id ?: 0
    val reservation = Reservation(
      id = lastIndex + 1,
      status = ReservationStatus.PENDING,
      movieId = movie.id,
      userId = userId,
      seats = seats
    )
    reservations.add(reservation)
    return reservation
  }

  fun updateStatus(paymentEvent: PaymentEvent) {
    val reservation = reservationsById()[paymentEvent.id] ?: throw ReservationNotFoundException()
    when (paymentEvent) {
      is PaymentSucceededEvent -> reservation.status = ReservationStatus.CONFIRMED
      is PaymentFailedEvent -> reservation.status = ReservationStatus.DENIED
    }
  }
}

class ReservationValidationException : RuntimeException("Not enough seats!")
class ReservationNotFoundException : RuntimeException("Reservation not found!")