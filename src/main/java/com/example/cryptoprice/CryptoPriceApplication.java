package com.example.cryptoprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoPriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoPriceApplication.class, args);
    }

}
