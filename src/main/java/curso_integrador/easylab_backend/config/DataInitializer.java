package curso_integrador.easylab_backend.config;

import curso_integrador.easylab_backend.model.Rol;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.model.PruebaLaboratorio;
import curso_integrador.easylab_backend.model.Paciente;
import curso_integrador.easylab_backend.model.Orden;
import curso_integrador.easylab_backend.model.Cita;
import curso_integrador.easylab_backend.repository.RolRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import curso_integrador.easylab_backend.repository.PruebaRepository;
import curso_integrador.easylab_backend.repository.PacienteRepository;
import curso_integrador.easylab_backend.repository.OrdenRepository;
import curso_integrador.easylab_backend.repository.CitaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Arrays;

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
            PruebaRepository pruebaRepository,
            PacienteRepository pacienteRepository,
            OrdenRepository ordenRepository,
            CitaRepository citaRepository,
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
            Usuario usrPaciente = usuarioRepository.save(new Usuario("12345678", passwordHash, "Juan Pérez García", paciente));
            Usuario usrMedico = usuarioRepository.save(new Usuario("45678", passwordHash, "Dra. María López Rivera", medico));
            usuarioRepository.save(new Usuario("recepcion@easylab.com", passwordHash, "Ana Torres Mendoza", recepcion));
            usuarioRepository.save(new Usuario("admin@easylab.com", passwordHash, "Carlos Admin Ruiz", admin));

            // Pruebas de laboratorio
            PruebaLaboratorio hemograma = new PruebaLaboratorio();
            hemograma.setNombre("Hemograma Completo");
            hemograma.setCodigo("HEM-001");
            hemograma.setCategoria("Hematología");
            hemograma = pruebaRepository.save(hemograma);

            PruebaLaboratorio perfilLipidico = new PruebaLaboratorio();
            perfilLipidico.setNombre("Perfil Lipídico");
            perfilLipidico.setCodigo("BIO-004");
            perfilLipidico.setCategoria("Bioquímica");
            perfilLipidico = pruebaRepository.save(perfilLipidico);

            PruebaLaboratorio glucosa = new PruebaLaboratorio();
            glucosa.setNombre("Glucosa");
            glucosa.setCodigo("BIO-001");
            glucosa.setCategoria("Bioquímica");
            glucosa = pruebaRepository.save(glucosa);

            PruebaLaboratorio orina = new PruebaLaboratorio();
            orina.setNombre("Examen General de Orina");
            orina.setCodigo("URI-001");
            orina.setCategoria("Urianálisis");
            orina = pruebaRepository.save(orina);

            // Pacientes de prueba
            Paciente p1 = new Paciente();
            p1.setDni("12345678");
            p1.setNombreCompleto("Juan Carlos García López");
            p1.setCorreo("juan@example.com");
            p1.setTelefono("987654321");
            p1 = pacienteRepository.save(p1);

            Paciente p2 = new Paciente();
            p2.setDni("87654321");
            p2.setNombreCompleto("María Elena Rodríguez Díaz");
            p2.setCorreo("maria@example.com");
            p2.setTelefono("912345678");
            p2 = pacienteRepository.save(p2);

            Paciente p3 = new Paciente();
            p3.setDni("11223344");
            p3.setNombreCompleto("Pedro José Martínez Sánchez");
            p3.setCorreo("pedro@example.com");
            p3.setTelefono("999888777");
            p3 = pacienteRepository.save(p3);

            // Ordenes de prueba
            Orden o1 = new Orden();
            o1.setNumeroOrden("ORD-001");
            o1.setPaciente(p1);
            o1.setMedico(usrMedico);
            o1.setFechaEmision(LocalDate.now());
            o1.setEstado("PENDIENTE");
            o1.setPruebas(new HashSet<>(Arrays.asList(hemograma, glucosa)));
            ordenRepository.save(o1);

            Orden o2 = new Orden();
            o2.setNumeroOrden("ORD-002");
            o2.setPaciente(p2);
            o2.setMedico(usrMedico);
            o2.setFechaEmision(LocalDate.now().minusDays(1));
            o2.setEstado("VIGENTE");
            o2.setPruebas(new HashSet<>(Arrays.asList(perfilLipidico)));
            ordenRepository.save(o2);

            Orden o3 = new Orden();
            o3.setNumeroOrden("ORD-003");
            o3.setPaciente(p3);
            o3.setMedico(usrMedico);
            o3.setFechaEmision(LocalDate.now().minusDays(2));
            o3.setEstado("ATENDIDA");
            o3.setPruebas(new HashSet<>(Arrays.asList(orina)));
            ordenRepository.save(o3);

            // Citas de prueba
            Cita c1 = new Cita();
            c1.setPaciente(p1);
            c1.setMedico(usrMedico);
            c1.setFecha(LocalDate.now());
            c1.setHora(java.time.LocalTime.of(9, 30));
            c1.setEstado("PROGRAMADA");
            citaRepository.save(c1);

            Cita c2 = new Cita();
            c2.setPaciente(p2);
            c2.setMedico(usrMedico);
            c2.setFecha(LocalDate.now());
            c2.setHora(java.time.LocalTime.of(10, 0));
            c2.setEstado("PROGRAMADA");
            citaRepository.save(c2);

            System.out.println("✅ Datos iniciales insertados correctamente.");
            System.out.println("📋 Usuarios de prueba (contraseña: 123456):");
            System.out.println("   - Paciente:  12345678");
            System.out.println("   - Médico:    45678");
            System.out.println("   - Recepción: recepcion@easylab.com");
            System.out.println("   - Admin:     admin@easylab.com");
        };
    }
}
