package com.busgo.fc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author busgo
 * @date 2019-11-18 15:07
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.busgo.fc.inner"})
public class FCApplication {


    public static void main(String[] args) {


        SpringApplication.run(FCApplication.class, args);
    }
}
