package com.iwom.theatre.reservation.service

import com.iwom.theatre.reservation.model.Reservation
import com.iwom.theatre.reservation.request.CreateReservationRequest
import org.springframework.stereotype.Service

@Service
class ReservationService {

  fun example(bodyIn: CreateReservationRequest) =
    Reservation(
      id = bodyIn.id + 10,
      name = bodyIn.name
    )
}