package com.house.care.registryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaServer
@EnableConfigServer
@EnableZuulProxy
public class RegistryServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(RegistryServerApplication.class, args);
    }

}
