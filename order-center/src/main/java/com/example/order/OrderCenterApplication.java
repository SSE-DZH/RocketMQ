package com.example.order;

import com.example.framework.mq.annotation.EnableRocketmq;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRocketmq
@MapperScan("com.example.order.mapper")
public class OrderCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderCenterApplication.class, args);
    }
}
