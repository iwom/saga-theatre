package com.iwom.theatre.payment.repository

import com.iwom.theatre.payment.model.PaymentDetails
import org.springframework.stereotype.Service

@Service
class PaymentRepository {
  private final val paymentDetails = listOf(
    PaymentDetails(userId = 1, creditCardNo = "1000 0000 0001"),
    PaymentDetails(userId = 2, creditCardNo = "2000 0000 0002"),
    PaymentDetails(userId = 3, creditCardNo = "3000 0000 0003")
  )
  private final val paymentDetailsByUserId = paymentDetails.associateBy { it.userId }

  fun validatePaymentByUserId(userId: Int) = paymentDetailsByUserId[userId] ?: throw PaymentNotFoundException()

  fun fetch() = paymentDetails
}

class PaymentNotFoundException : RuntimeException("Payment validation exception")