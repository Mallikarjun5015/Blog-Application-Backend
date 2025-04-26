package com.mallu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "JWT";

    @Bean
    public OpenAPI blogApplicationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BLOG-APPLICATION")
                        .description("Code Developed by: Mallikarjun Jamadar..")
                        .version("1.0")
                        .termsOfService("Terms of service")
                        .contact(new Contact()
                                .name("Mallu J.")
                                .url("https://www.instagram.com/apna_bhidu_2341/?__pwa=1")
                                .email("mallikarjunjamadar777@gmail.com"))
                        .license(new License()
                                .name("PENTAGON SPACE, Bengaluru")
                                .url("http://pentagonspace.in")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, 
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)));
    }
}
