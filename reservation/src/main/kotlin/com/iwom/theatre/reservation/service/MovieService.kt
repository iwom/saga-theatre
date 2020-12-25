package com.iwom.theatre.reservation.service

import com.iwom.theatre.reservation.model.Movie
import org.springframework.stereotype.Service

@Service
class MovieService {
  private final val movies: List<Movie> = listOf(
    Movie(id = 1, name = "Seven", year = 1995, price = 5, maxSeats = 10),
    Movie(id = 2, name = "Interstellar", year = 2014, price = 2, maxSeats = 50),
    Movie(id = 3, name = "Stalker", year = 1979, price = 3, maxSeats = 20)
  )

  val moviesById = movies.associateBy { it.id }

  fun fetch() = movies

  fun getById(id: Int) = moviesById[id] ?: throw MovieNotFoundException()
}

class MovieNotFoundException : RuntimeException("Movie not found!")