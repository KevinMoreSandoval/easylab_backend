package curso_integrador.easylab_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger / OpenAPI 3.
 *
 * - Define la información general de la API (título, versión, descripción).
 * - Configura el esquema de seguridad JWT Bearer para que se pueda
 *   autenticar directamente desde la UI de Swagger.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI easylabOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyLab API")
                        .version("1.0.0")
                        .description("API REST del sistema EasyLab — gestión de laboratorios clínicos. "
                                + "Incluye autenticación JWT, gestión de usuarios, pacientes, exámenes y resultados.")
                        .contact(new Contact()
                                .name("Equipo EasyLab")
                                .email("soporte@easylab.com")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Ingresa tu token JWT (sin el prefijo 'Bearer ')")));
    }
}
