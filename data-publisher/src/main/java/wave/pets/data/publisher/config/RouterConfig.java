package wave.pets.data.publisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import wave.pets.data.publisher.handler.CollarHandler;
import wave.pets.data.publisher.handler.DataHandler;
import wave.pets.data.publisher.handler.PetHandler;
import wave.pets.data.publisher.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(DataHandler dataHandler,
                                                 UserHandler userHandler,
                                                 PetHandler petHandler,
                                                 CollarHandler collarHandler) {
        return route(POST("/message/create"), dataHandler::publish)
                .andRoute(POST("/user/create"), userHandler::createUser)
                .andRoute(PUT("/user/update"), userHandler::updateUser)
                .andRoute(DELETE("/user/delete"), userHandler::deleteUser)
                .andRoute(POST("/pet/create"), petHandler::createPet)
                .andRoute(PUT("/pet/update"), petHandler::updatePet)
                .andRoute(DELETE("/pet/delete"), petHandler::deletePet)
                .andRoute(POST("/collar/create"), collarHandler::createCollar)
                .andRoute(PUT("/collar/update"), collarHandler::updateCollar)
                .andRoute(DELETE("/collar/delete"), collarHandler::deleteCollar);
    }
}
