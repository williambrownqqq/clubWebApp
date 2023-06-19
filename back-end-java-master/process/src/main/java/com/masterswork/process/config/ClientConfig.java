package com.masterswork.process.config;

import com.masterswork.process.service.SecurityUtils;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.masterswork.process.client")
public class ClientConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER_SCHEMA = "Bearer ";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> SecurityUtils.getJwt().ifPresent(
                jwt -> requestTemplate.header(AUTHORIZATION_HEADER, BEARER_SCHEMA + jwt)
        );
    }

}
