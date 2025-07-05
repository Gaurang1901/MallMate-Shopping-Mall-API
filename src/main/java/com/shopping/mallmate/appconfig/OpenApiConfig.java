package com.shopping.mallmate.appconfig;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI mallMateOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("MallMate Shopping Mall API")
                                                .description("API documentation for MallMate Shopping Mall application.")
                                                .version("v1.0.0")
                                                .contact(new Contact().name("MallMate Team")
                                                                .email("theredx1901@gmail.com"))
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                                .externalDocs(new ExternalDocumentation()
                                                .description("MallMate GitHub Repository")
                                                .url("https://github.com/Gaurang1901/MallMate-Shopping-Mall-API"));
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                                .group("public")
                                .pathsToMatch("/api/**", "/admin/**", "/auth/**", "/")
                                .build();
        }
}