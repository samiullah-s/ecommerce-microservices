package com.example.order.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI orderServiceOpenAPI() {
                Server server = new Server();
                server.setUrl("http://localhost:9004");
                server.setDescription("Development server");

                Contact contact = new Contact();
                contact.setName("E-Commerce Team");
                contact.setEmail("support@ecommerce.com");

                Info info = new Info()
                                .title("Order Service API")
                                .version("1.0.0")
                                .description("API for processing and managing orders. " +
                                                "Supports order creation and retrieval with role-based access control.")
                                .contact(contact);

                SecurityScheme securityScheme = new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT token obtained from authentication service");

                SecurityRequirement securityRequirement = new SecurityRequirement()
                                .addList("Bearer Authentication");

                return new OpenAPI()
                                .info(info)
                                .servers(List.of(server))
                                .components(new Components().addSecuritySchemes("Bearer Authentication",
                                                securityScheme))
                                .addSecurityItem(securityRequirement);
        }
}
