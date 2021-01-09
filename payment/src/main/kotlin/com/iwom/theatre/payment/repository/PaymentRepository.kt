package com.iwom.theatre.payment.repository

import com.iwom.theatre.payment.model.PaymentDetails
import org.springframework.stereotype.Service

@Service
class PaymentRepository {
  private final val paymentDetails = listOf(
    PaymentDetails(userId = 1, name = "Josh Yellow", limit = 500, total = 0),
    PaymentDetails(userId = 2, name = "George A.", limit = 1000, total = 0),
    PaymentDetails(userId = 3, name = "Mike Brown", limit = 1200, total = 850),
    PaymentDetails(userId = 4, name = "J. H. Blue", limit = 1000, total = 980),
  )
  private final val paymentDetailsByUserId = paymentDetails.associateBy { it.userId }

  fun fetch() = paymentDetails

  fun getByUserId(userId: Int) = paymentDetailsByUserId[userId] ?: throw PaymentNotFoundException()

  fun updateTotalByUserId(userId: Int, newTotal: Int) = paymentDetailsByUserId[userId]?.apply {
    total = newTotal
  } ?: throw PaymentNotFoundException()
}

class PaymentNotFoundException : RuntimeException("Payment not found")