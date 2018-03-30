package com.example.reactiveweb;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@SpringBootApplication
public class ReactiveWebApplication {

		@Bean
		ApplicationRunner run(CustomerRepository customerRepository) {
				return args ->
						customerRepository
							.deleteAll()
							.thenMany(Flux.just("a@a.com", "b@b.com")
								.map(email -> new Customer(null, email))
								.flatMap(customerRepository::save))
							.thenMany(customerRepository.findAll())
							.subscribe(System.out::println);
		}

		public static void main(String[] string) {
				SpringApplication.run(ReactiveWebApplication.class, string);
		}
}

@Configuration
class JavaConfiguration {

		@Bean
		RouterFunction<ServerResponse> javaRoutes(CustomerRepository customerRepository) {
				return route(GET("/customers"), request -> ok().body(customerRepository.findAll(), Customer.class));
		}
}

/*
@RestController
class CustomerRestController {

		private final CustomerRepository customerRepository;

		CustomerRestController(CustomerRepository customerRepository) {
				this.customerRepository = customerRepository;
		}

		@GetMapping("/customers")
		Flux<Customer> customers() {
				return this.customerRepository.findAll();
		}
}
*/
