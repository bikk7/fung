package com.example.zj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.zj")
@MapperScan(basePackages = "com.example.zj.dao")

public class ZjApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZjApplication.class, args);
    }
}
