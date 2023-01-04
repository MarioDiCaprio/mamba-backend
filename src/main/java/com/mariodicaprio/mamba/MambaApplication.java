package com.mariodicaprio.mamba;


import com.mariodicaprio.mamba.security.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class MambaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MambaApplication.class, args);
    }

}
