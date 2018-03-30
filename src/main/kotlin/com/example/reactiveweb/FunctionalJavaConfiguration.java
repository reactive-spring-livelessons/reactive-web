package com.example.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Configuration
public class FunctionalJavaConfiguration {

		Mono<ServerResponse> sse(ServerRequest request) {
				Flux<Greeting> greetingFlux = Flux.<Greeting>generate(sink -> sink.next(new Greeting("Hello, world @ " + Instant.now().toString())))
					.delayElements(Duration.ofSeconds(1));

				return ServerResponse
					.ok()
					.contentType(MediaType.TEXT_EVENT_STREAM)
					.body(greetingFlux, Greeting.class);
		}

		@Bean
		RouterFunction<ServerResponse> routes() {
				return route(GET("/frp/greetings"), request -> ServerResponse.ok().body(Flux.just("Hello, world"), String.class))
					.andRoute(GET("/frp/hi"), r -> ServerResponse.ok().body(Flux.just("Hi"), String.class))
					.andRoute(GET("/frp/sse"), this::sse);
		}
}
