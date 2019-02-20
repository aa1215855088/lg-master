package com.lg.biz.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class LgBizAWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(LgBizAWebApplication.class, args);
    }
}
