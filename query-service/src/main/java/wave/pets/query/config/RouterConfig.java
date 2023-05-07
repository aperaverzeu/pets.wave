package wave.pets.query.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import wave.pets.query.handler.MessageHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(MessageHandler messageHandler) {
        return route(GET("/"), request -> messageHandler.getAllMessages())
                .andRoute(GET("/one/{id}"), request -> messageHandler.getMessageById(request.pathVariable("id")));
    }
}
