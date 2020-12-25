package com.iwom.theatre.payment.service

import com.iwom.theatre.payment.model.PaymentDetails
import org.springframework.stereotype.Service

@Service
class PaymentService {
  private final val paymentDetails = listOf(
    PaymentDetails(userId = 1, creditCardNo = "ABC123"),
    PaymentDetails(userId = 2, creditCardNo = "ABC123"),
    PaymentDetails(userId = 3, creditCardNo = "ABC123")
  )
  private final val paymentDetailsByUserId = paymentDetails.associateBy { it.userId }

  fun validatePayment(userId: Int) = paymentDetailsByUserId[userId] ?: throw PaymentValidationException()
}

class PaymentValidationException : RuntimeException("Payment validation exception")