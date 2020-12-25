package com.iwom.theatre.payment

import com.iwom.theatre.payment.service.PaymentService
import org.apache.cxf.Bus
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class PaymentConfiguration {

  @Bean
  fun dispatcherServlet(): ServletRegistrationBean<CXFServlet>? {
    return ServletRegistrationBean(CXFServlet(), "/soap-api/*")
  }

  @Bean
  @Primary
  fun dispatcherServletPathProvider(): DispatcherServletPath? {
    return DispatcherServletPath { "" }
  }

  @Bean
  fun endpoint(bus: Bus?, paymentService: PaymentService?): EndpointImpl {
    val endpoint = EndpointImpl(bus, paymentService)
    endpoint.publish("/service/payment")
    return endpoint
  }
}