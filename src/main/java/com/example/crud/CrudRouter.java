package com.example.crud;

import com.example.crud.handlers.AuthHandler;
import com.example.crud.handlers.UserHandler;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CrudRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {
        return RouterFunctions
                .route(POST("/users").and(contentType(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::listUser)
                .andRoute(GET("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(PUT("/users").and(accept(APPLICATION_JSON)), userHandler :: createUser)
                .andRoute(DELETE("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler :: deleteUser)
                .andRoute(GET("/users/events/stream").and(accept(APPLICATION_JSON)), userHandler :: streamEvents);
    }

    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthHandler authHandler) {
        return RouterFunctions
                .route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/auth/signup").and(accept(APPLICATION_JSON)), authHandler::signUp);
    }
}
