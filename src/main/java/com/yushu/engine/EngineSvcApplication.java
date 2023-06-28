package com.yushu.engine;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@Import(NettyServerConfig.class)
public class EngineSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineSvcApplication.class, args);
    }

}
