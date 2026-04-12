package com.sportsbooking.emailapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableConfigurationProperties
public class EmailApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner checkMailConfig(JavaMailSender mailSender) {
        return args -> {
            JavaMailSenderImpl impl = (JavaMailSenderImpl) mailSender;
            System.out.println("=== CONFIGURACIÓN SMTP ===");
            System.out.println("Host: " + impl.getHost());
            System.out.println("Port: " + impl.getPort());
            System.out.println("Properties: " + impl.getJavaMailProperties());
        };
    }

}
