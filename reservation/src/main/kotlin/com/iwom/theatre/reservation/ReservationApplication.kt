package com.iwom.theatre.reservation

import org.apache.camel.component.servlet.CamelHttpTransportServlet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ReservationApplication {

  @Value("\${reservation.api.v1}")
  lateinit var contextPath: String

  @Bean
  fun servletRegistrationBean(): ServletRegistrationBean<*>? {
    val servlet: ServletRegistrationBean<*> = ServletRegistrationBean(CamelHttpTransportServlet(), "$contextPath/*")
    servlet.setName("CamelServlet")
    return servlet
  }
}

fun main(args: Array<String>) {
  runApplication<ReservationApplication>(*args)
}