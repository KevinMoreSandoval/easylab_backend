package curso_integrador.easylab_backend.config;

import curso_integrador.easylab_backend.model.Rol;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.RolRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Inicializa datos de prueba en la base de datos al arrancar la aplicación.
 *
 * Crea los 4 roles y un usuario de ejemplo por cada rol (contraseña: 123456).
 * Solo inserta datos si la tabla de roles está vacía (primera ejecución).
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Solo inicializar si no hay roles en la BD
            if (rolRepository.count() > 0) {
                return;
            }

            // Crear roles
            Rol paciente = rolRepository.save(new Rol("PACIENTE"));
            Rol medico = rolRepository.save(new Rol("MEDICO"));
            Rol recepcion = rolRepository.save(new Rol("RECEPCION"));
            Rol admin = rolRepository.save(new Rol("ADMIN"));

            String passwordHash = passwordEncoder.encode("123456");

            // Usuarios de prueba
            usuarioRepository.save(new Usuario("12345678", passwordHash, "Juan Pérez García", paciente));
            usuarioRepository.save(new Usuario("45678", passwordHash, "Dra. María López Rivera", medico));
            usuarioRepository.save(new Usuario("recepcion@easylab.com", passwordHash, "Ana Torres Mendoza", recepcion));
            usuarioRepository.save(new Usuario("admin@easylab.com", passwordHash, "Carlos Admin Ruiz", admin));

            System.out.println("✅ Datos iniciales insertados correctamente.");
            System.out.println("📋 Usuarios de prueba (contraseña: 123456):");
            System.out.println("   - Paciente:  12345678");
            System.out.println("   - Médico:    45678");
            System.out.println("   - Recepción: recepcion@easylab.com");
            System.out.println("   - Admin:     admin@easylab.com");
        };
    }
}
