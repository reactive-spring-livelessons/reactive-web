package com.example.reactiveweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Consumer;

/**
	* @author <a href="mailto:josh@joshlong.com">Josh Long</a>
	*/
@Configuration
class GreetingsWebSocketController {

		@Bean
		WebSocketHandlerAdapter wsha() {
				return new WebSocketHandlerAdapter();
		}

		@Bean
		WebSocketHandler wsHandler() {
				return session ->
					session.send(
						Flux.generate((Consumer<SynchronousSink<WebSocketMessage>>) sink -> sink.next(session.textMessage("Hello " + System.currentTimeMillis())))
							.delayElements(Duration.ofSeconds(1)));
		}

		@Bean
		HandlerMapping simpleUrlHandlerMapping() {
				SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
				simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/ws/hello", wsHandler()));
				simpleUrlHandlerMapping.setOrder(10);
				return simpleUrlHandlerMapping;
		}
}