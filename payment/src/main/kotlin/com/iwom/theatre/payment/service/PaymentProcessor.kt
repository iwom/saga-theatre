package com.iwom.theatre.payment.service

import com.iwom.theatre.payment.repository.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentProcessor constructor(
  private val paymentRepository: PaymentRepository
) {

  fun processPayment(userId: Int, price: Int) = try {
    val paymentDetails = paymentRepository.getByUserId(userId)
    if (paymentDetails.total + price <= paymentDetails.limit) {
      paymentRepository.updateTotalByUserId(userId, paymentDetails.total + price)
    } else {
      throw DebitTooHighException(userId)
    }
  } catch (exception: RuntimeException) {
    throw PaymentValidationException(exception)
  }
}

class DebitTooHighException(userId: Int) : RuntimeException("User [$userId] has too high debit")

class PaymentValidationException(cause: Throwable) : RuntimeException("Payment validation failed", cause)