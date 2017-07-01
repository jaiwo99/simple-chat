package com.jaiwo99.demo.simplechat;

import com.jaiwo99.demo.simplechat.config.SinglePageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({SinglePageConfiguration.class})
@ComponentScan(excludeFilters = @ComponentScan.Filter(Configuration.class))
@SpringBootApplication
public class ChatApp {
    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }
}