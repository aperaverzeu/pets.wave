package wave.pets.query.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import wave.pets.query.handler.CollarHandler;
import wave.pets.query.handler.PetHandler;
import wave.pets.query.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(UserHandler userHandler,
                                                 PetHandler petHandler,
                                                 CollarHandler collarHandler) {
        return route(GET("/users/"), userHandler::getAll)
                .andRoute(GET("/users/{id}"), userHandler::getUserById)
                .andRoute(GET("/pets/"), petHandler::getAll)
                .andRoute(GET("/pets/{id}"), petHandler::getPetById)
                .andRoute(GET("/pets/user/{user_id}"), petHandler::getAllPetsByUserId)
                .andRoute(GET("/collars/"), collarHandler::getAll)
                .andRoute(GET("/collars/{id}"), collarHandler::getCollarById);
    }
}
