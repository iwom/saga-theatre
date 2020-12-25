package com.iwom.theatre.payment.service

import com.iwom.theatre.payment.model.PaymentDetails
import com.iwom.theatre.payment.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.jws.WebService

@WebService
@Service
class PaymentService {

  @Autowired
  private lateinit var repository: PaymentRepository

  fun fetchPayments(): List<PaymentDetails> = repository.fetch()
}