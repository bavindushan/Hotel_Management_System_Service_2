package edu.esoft.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
        System.setProperty("JWT_EXPIRATION_TIME", dotenv.get("JWT_EXPIRATION_TIME"));

        SpringApplication.run(Main.class);
    }
}