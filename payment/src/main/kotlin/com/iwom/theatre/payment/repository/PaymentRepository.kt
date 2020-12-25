package com.iwom.theatre.payment.repository

import com.iwom.theatre.payment.model.PaymentDetails
import org.springframework.stereotype.Service

@Service
class PaymentRepository {
  private final val paymentDetails = listOf(
    PaymentDetails(userId = 1, creditCardNo = "ABC123"),
    PaymentDetails(userId = 2, creditCardNo = "ABC124"),
    PaymentDetails(userId = 3, creditCardNo = "ABC125")
  )
  private final val paymentDetailsByUserId = paymentDetails.associateBy { it.userId }

  fun validatePaymentByUserId(userId: Int) = paymentDetailsByUserId[userId] ?: throw PaymentNotFoundException()

  fun fetch() = paymentDetails
}

class PaymentNotFoundException : RuntimeException("Payment validation exception")