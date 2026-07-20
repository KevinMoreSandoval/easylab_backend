package curso_integrador.easylab_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Configuración CORS para permitir peticiones del frontend React.
 *
 * En desarrollo, el frontend corre en http://localhost:5173 (Vite)
 * y necesita hacer peticiones al backend en http://localhost:8080.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Leer orígenes desde la variable de entorno, o usar los locales por defecto
        String allowedOrigins = System.getenv("ALLOWED_ORIGINS");
        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
            config.setAllowedOrigins(java.util.Arrays.asList(allowedOrigins.split(",")));
        } else {
            config.setAllowedOrigins(List.of(
                    "http://localhost:5173",
                    "http://localhost:5174",
                    "http://localhost:3000"
            ));
        }

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
