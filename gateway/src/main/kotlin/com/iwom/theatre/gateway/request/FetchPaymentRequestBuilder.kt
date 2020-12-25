package com.iwom.theatre.gateway.request

import com.iwom.theatre.payment.service.FetchPayments

class FetchPaymentsRequestBuilder {
  fun fetchPayments(): FetchPayments = FetchPayments()
}