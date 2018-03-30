package com.example.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Configuration
public class WebsocketConfiguration {

		@Bean
		WebSocketHandlerAdapter webSocketHandlerAdapter() {
				return new WebSocketHandlerAdapter();
		}

		@Bean
		WebSocketHandler webSocketHandler() {
				return session -> {

						Flux<WebSocketMessage> messageFlux = Flux
							.<Greeting>generate(sink -> sink.next(new Greeting("Hello @" + Instant.now().toString())))
							.map(g -> session.textMessage(g.getText()))
							.delayElements(Duration.ofSeconds(1))
							.doFinally(signalType -> System.out.println("goodbye."));

						return session.send(messageFlux);
				};
		}

		@Bean
		HandlerMapping handlerMapping() {
				SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
				simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/ws/hello", webSocketHandler()));
				simpleUrlHandlerMapping.setOrder(10);
				return simpleUrlHandlerMapping;
		}
}
