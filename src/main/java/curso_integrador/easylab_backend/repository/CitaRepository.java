package curso_integrador.easylab_backend.repository;

import curso_integrador.easylab_backend.model.Cita;
import curso_integrador.easylab_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /** Citas de un médico en un estado dado, ordenadas por fecha y hora */
    List<Cita> findByMedicoAndEstadoOrderByFechaAscHoraAsc(Usuario medico, String estado);

    /** Citas de un paciente (por DNI) en un estado dado */
    List<Cita> findByPacienteDniAndEstado(String dni, String estado);

    /** Contar citas por fecha y estado (para dashboard) */
    long countByFechaAndEstado(LocalDate fecha, String estado);

    /** Contar citas por fecha (total del día) */
    long countByFecha(LocalDate fecha);
}
