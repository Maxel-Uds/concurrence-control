package com.project.concurrence.control.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfig {


    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("com.project.concurrence.control")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("API feita na Rinha de Backend segunda edição - 2024")
                        .contact(new Contact().name("Maxel Udson").url("https://github.com/Maxel-Uds").email("maxellopes32@gmail.com"))
                        .description("Esta API simula um sistema bancário onde ocorrem débitos e créditos")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}
