package com.chirping.gmall.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.chirping.gmall.mapper"})
public class GmallItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallItemServiceApplication.class, args);
    }

}
