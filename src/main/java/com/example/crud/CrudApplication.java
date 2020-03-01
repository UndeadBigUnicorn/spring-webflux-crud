package com.example.crud;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			userRepository.deleteAll()
					.thenMany(Flux.just(
							new User("danil", "12345")
					)
						.flatMap(userRepository::save))
					.thenMany(userRepository.findAll())
					.subscribe(System.out::println);
		};
	}
}
