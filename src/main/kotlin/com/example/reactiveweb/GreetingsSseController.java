package com.example.reactiveweb;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@RestController
public class GreetingsSseController {

		@GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
		Flux<Greeting> greetings() {
				return Flux
					.generate((Consumer<SynchronousSink<Greeting>>) sink -> sink.next(new Greeting("Hello, @ " + Instant.now().toString())))
					.delayElements(Duration.ofSeconds(1));
		}
}

class Greeting {
		public String message;

		public Greeting(String message) {
				this.message = message;
		}
}