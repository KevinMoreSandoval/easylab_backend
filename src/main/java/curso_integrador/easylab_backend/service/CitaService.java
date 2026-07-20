package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.CitaRequest;
import curso_integrador.easylab_backend.dto.CitaResponse;
import curso_integrador.easylab_backend.model.Cita;
import curso_integrador.easylab_backend.model.Paciente;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.CitaRepository;
import curso_integrador.easylab_backend.repository.PacienteRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de gestión de citas médicas.
 *
 * Responsabilidades:
 *  - Crear citas vinculando paciente y médico.
 *  - Obtener citas pendientes para el médico autenticado.
 *  - Cambiar estado de una cita (PROGRAMADA → ATENDIDA / CANCELADA).
 *  - Listar todas las citas (para recepción).
 */
@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    public CitaService(CitaRepository citaRepository,
                       PacienteRepository pacienteRepository,
                       UsuarioRepository usuarioRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** Crear una nueva cita médica */
    public CitaResponse crear(CitaRequest req) {
        Paciente paciente = pacienteRepository.findByDni(req.getPacienteDni())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + req.getPacienteDni()));

        Usuario medico = usuarioRepository.findById(req.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con ID: " + req.getMedicoId()));

        if (!medico.getRol().getNombre().equals("MEDICO")) {
            throw new IllegalArgumentException("El usuario seleccionado no tiene rol MEDICO.");
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha(LocalDate.parse(req.getFecha()));
        cita.setHora(LocalTime.parse(req.getHora()));
        cita.setEstado("PROGRAMADA");

        return toResponse(citaRepository.save(cita));
    }

    /** Obtener citas pendientes (PROGRAMADA) para el médico autenticado */
    public List<CitaResponse> citasPendientesMedico() {
        String identificador = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario medico = usuarioRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        return citaRepository.findByMedicoAndEstadoOrderByFechaAscHoraAsc(medico, "PROGRAMADA")
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Cambiar estado de una cita */
    public CitaResponse cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        cita.setEstado(nuevoEstado);
        return toResponse(citaRepository.save(cita));
    }

    /** Listar todas las citas (para recepción) */
    public List<CitaResponse> listarTodas() {
        return citaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Estadísticas para el dashboard de recepción */
    public long contarCitasHoy() {
        return citaRepository.countByFecha(LocalDate.now());
    }

    public long contarCitasProgramadasHoy() {
        return citaRepository.countByFechaAndEstado(LocalDate.now(), "PROGRAMADA");
    }

    private CitaResponse toResponse(Cita c) {
        CitaResponse r = new CitaResponse();
        r.setId(c.getId());
        r.setPacienteNombre(c.getPaciente().getNombreCompleto());
        r.setPacienteDni(c.getPaciente().getDni());
        r.setMedicoNombre(c.getMedico().getNombreCompleto());
        r.setMedicoId(c.getMedico().getId());
        r.setFecha(c.getFecha().toString());
        r.setHora(c.getHora().toString());
        r.setEstado(c.getEstado());
        return r;
    }
}
