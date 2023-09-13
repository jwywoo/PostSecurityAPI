package com.example.postsecurityapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostSecurityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostSecurityApiApplication.class, args);
    }

}
