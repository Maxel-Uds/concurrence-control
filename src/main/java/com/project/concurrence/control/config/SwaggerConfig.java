package com.project.concurrence.control.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final Response m200 = simpleMessage(String.valueOf(HttpStatus.OK.value()), "Transação realizada com sucesso");
    private final Response m404 = simpleMessage(String.valueOf(HttpStatus.NOT_FOUND.value()), "Usuário não encontrado");
    private final Response m422 = simpleMessage(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), "Erro de validação");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, Arrays.asList(m200, m404))
                .globalResponses(HttpMethod.POST, Arrays.asList(m200, m404, m422))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.project.concurrence.control"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API feita na Rinha de Backend segunda edição - 2024",
                "Esta API simula um sistema bancário onde ocorrem débitos e créditos",
                "Versão 1.0",
                "",
                new Contact("Maxel Udson", "https://github.com/Maxel-Uds", "maxellopes32@gmail.com"),
                "",
                "",
                Collections.emptyList() // Vendor Extensions
        );
    }

    private Response simpleMessage(String code, String msg) {
        return new ResponseBuilder().code(code).description(msg).build();
    }
}
