package com.octopus.friends;

import com.octopus.friends.domain.User;
import com.octopus.friends.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.octopus.friends","com.octopus.friends.repository"})
public class FriendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendsApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository) throws Exception{
        return (args) -> {
            User user = userRepository.save(User.builder()
                    .userName("test")
                    .userId("test")
                    .birth("2022.09.17")
                    .email("test@test.com")
                    .password("test")
                    .build());

            User user1 = userRepository.save(User.builder()
                    .userName("test1")
                    .userId("test1")
                    .birth("2022.09.17")
                    .email("test1@test.com")
                    .password("test1")
                    .build());
        };

    }

}
