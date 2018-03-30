package com.example.reactiveweb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

/**
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
@Configuration
class KotlinConfiguration(
		private val customerRepository: CustomerRepository) {

	@Bean
	fun kotlinRoutes() = router {

		GET("/customers/{id}") {
			ServerResponse.ok().body(customerRepository.findById(it.pathVariable("id")))
		}
	}
}