package com.example.crud.handlers;

import com.example.crud.model.ApiResponse;
import com.example.crud.security.TokenProvider;
import com.example.crud.model.AuthToken;
import com.example.crud.security.model.AuthRequest;
import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<AuthRequest> loginRequests = request.bodyToMono(AuthRequest.class);
        return loginRequests.flatMap(login -> userRepository.findByUsername(login.getUsername())
                .flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(new AuthToken(tokenProvider.generateToken(user))));
                    } else {
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "Invalid credentials", null)));
                    }
                }).switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(400, "User does not exist", null))))
        );
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.map(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return user;
        }).flatMap(user -> userRepository.findByUsername(user.getUsername())
                .flatMap(dbUser -> ServerResponse.badRequest()
                        .body(BodyInserters.fromValue(new ApiResponse(400, "User already exist", null))))
                .switchIfEmpty(userRepository.save(user).flatMap(savedUser -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(savedUser)))));
    }
}
