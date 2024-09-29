package com.example.points;

import com.example.framework.mq.annotation.EnableRocketmq;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRocketmq
@MapperScan("com.example.points.mapper")
public class PointsCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PointsCenterApplication.class,args);
    }
}
