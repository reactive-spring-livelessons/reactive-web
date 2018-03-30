package com.example.reactiveweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@SpringBootApplication
class ReactiveWebApplication

fun main(args: Array<String>) {
	SpringApplicationBuilder()
			.sources(ReactiveWebApplication::class.java)
			.initializers(beans {
				bean {
					router {
						GET("/frp/kotlin/hi") {
							ServerResponse.ok().body(Flux.just("Hello"))
						}
					}
				}
			})
			.run(*args)
}
