package com.itqf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.itqf.config")  //扫描servlet的相关注解
@MapperScan(basePackages = "com.itqf.mapper")
public class SpringbootRzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRzApplication.class, args);
    }

}
