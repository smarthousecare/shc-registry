package com.house.care.registryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableOAuth2Sso
@EnableConfigServer
public class RegistryServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(RegistryServerApplication.class, args);
    }

}
