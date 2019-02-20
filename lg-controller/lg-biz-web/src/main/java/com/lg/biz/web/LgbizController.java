package com.lg.biz.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.lg.biz.mapper")
@SpringBootApplication
public class LgbizController {
    public static void main(String[] args) {
        SpringApplication.run(LgbizController.class, args);
    }
}
