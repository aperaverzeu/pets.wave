package wave.pets.data.to.kafka.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import wave.pets.data.to.kafka.api.DataHandler;
import wave.pets.data.to.kafka.api.request.MessageRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DataRouter {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/",
                    produces = {APPLICATION_JSON_VALUE},
                    method = GET,
                    beanClass = DataHandler.class,
                    beanMethod = "index",
                    operation = @Operation(
                            operationId = "index",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = String.class)))})),
            @RouterOperation(
                    path = "/data",
                    produces = {APPLICATION_JSON_VALUE},
                    method = POST,
                    beanClass = DataHandler.class,
                    beanMethod = "publish",
                    operation = @Operation(
                            operationId = "publish",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Successful operation",
                                            content = @Content(schema = @Schema(implementation = String.class))),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "Wrong operation")
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = MessageRequest.class)))))})
    public RouterFunction<ServerResponse> routes(DataHandler dataHandler) {
        return route(GET("/"), dataHandler::index).andRoute(POST("/data"), dataHandler::publish);
    }
}
