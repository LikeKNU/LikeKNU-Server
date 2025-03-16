package com.woopaca.likeknu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.woopaca.likeknu", "com.woopaca.univclub"})
public class LikeKnuApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeKnuApplication.class, args);
    }

}
