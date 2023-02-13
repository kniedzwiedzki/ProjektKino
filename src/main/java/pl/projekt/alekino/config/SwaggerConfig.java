package pl.projekt.alekino.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = " Projekt Ale Kino", version = "${app.version}", description = "Ale Kino documentation"))
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        String[] packages = {"pl.projekt.alekino"};
        return GroupedOpenApi.builder().group("all").packagesToScan(packages).build();
    }

    @Bean
    public GroupedOpenApi movieOpenApi() {
        String[] packages = {"pl.projekt.alekino.domain.movie"};
        return GroupedOpenApi.builder().group("movie").packagesToScan(packages).build();
    }

    @Bean
    public GroupedOpenApi authOpenApi() {
        String[] packages = {"pl.projekt.alekino.auth"};
        return GroupedOpenApi.builder().group("auth").packagesToScan(packages).build();
    }

    @Bean
    public GroupedOpenApi usersOpenApi() {
        String[] packages = {"pl.projekt.alekino.domain.user"};
        return GroupedOpenApi.builder().group("users").packagesToScan(packages).build();
    }

}
