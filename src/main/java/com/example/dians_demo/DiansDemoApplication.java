package com.example.dians_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DiansDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiansDemoApplication.class, args);
    }

}
