package org.bookstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer",
        in = SecuritySchemeIn.HEADER, bearerFormat = "JWT")
@SecurityScheme(name = "BasicAuth", type = SecuritySchemeType.HTTP, scheme = "basic",
        in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(
        info = @Info(title = "Books API", description = "Book store app"),
        security = {
                @SecurityRequirement(name = "BearerAuth"),
                @SecurityRequirement(name = "BasicAuth")}
)
public class OpenApiConfig {
}
