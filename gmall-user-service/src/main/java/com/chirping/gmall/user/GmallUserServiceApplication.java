package com.chirping.gmall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 刘志鹏
 * @date $date$
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.chirping.gmall.mapper"})
public class GmallUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallUserServiceApplication.class, args);
    }
}
