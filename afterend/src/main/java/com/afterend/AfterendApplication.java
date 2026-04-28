package com.afterend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AfterendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AfterendApplication.class, args);
        System.out.println("学友圈启动成功！");
    }
}
