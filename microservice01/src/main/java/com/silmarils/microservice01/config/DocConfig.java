package com.silmarils.microservice01.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(
                new Info().title("Microservice01")
                        .version("1.0")
                        .description("Microservice01 tem a função de fazer a conexão com o microservice02 e por assim puxar os dados do banco de dados")
                        .contact(new Contact().email("equipesilmarils@gmail.com").url("https://github.com/equipesilmarils"))


        );
    }
}
