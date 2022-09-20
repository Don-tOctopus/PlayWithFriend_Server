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



}
