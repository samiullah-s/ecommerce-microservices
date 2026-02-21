package com.example.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI authServiceOpenAPI() {
                Server server = new Server();
                server.setUrl("http://localhost:9001");
                server.setDescription("Development server");

                Contact contact = new Contact();
                contact.setName("E-Commerce Team");
                contact.setEmail("support@ecommerce.com");

                Info info = new Info()
                                .title("Authentication Service API")
                                .version("1.0.0")
                                .description("API for user registration and authentication.")
                                .contact(contact);

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(server));
        }
}
