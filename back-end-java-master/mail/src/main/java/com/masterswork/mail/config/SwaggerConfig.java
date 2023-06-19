package com.masterswork.mail.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final static String JWT_AUTHORIZATION_NAME = "bearer token";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(JWT_AUTHORIZATION_NAME))
                .components(new Components()
                    .addSecuritySchemes(JWT_AUTHORIZATION_NAME, new SecurityScheme()
                            .name(JWT_AUTHORIZATION_NAME)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    ));
    }
}
