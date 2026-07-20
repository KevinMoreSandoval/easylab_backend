package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.PacienteDTO;
import curso_integrador.easylab_backend.dto.PruebaResponse;
import curso_integrador.easylab_backend.model.Orden;
import curso_integrador.easylab_backend.model.Paciente;
import curso_integrador.easylab_backend.model.PruebaLaboratorio;
import curso_integrador.easylab_backend.repository.OrdenRepository;
import curso_integrador.easylab_backend.repository.PacienteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final OrdenRepository ordenRepository;
    private final PruebaService pruebaService;

    public PacienteService(PacienteRepository pacienteRepository,
                           OrdenRepository ordenRepository,
                           PruebaService pruebaService) {
        this.pacienteRepository = pacienteRepository;
        this.ordenRepository = ordenRepository;
        this.pruebaService = pruebaService;
    }

    /** Find a patient by DNI */
    public PacienteDTO findByDni(String dni) {
        Paciente p = pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
        return toDTO(p);
    }

    /** Register a new patient */
    public PacienteDTO create(PacienteDTO dto) {
        if (pacienteRepository.existsByDni(dto.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + dto.getDni());
        }
        Paciente p = new Paciente();
        p.setNombreCompleto(dto.getNombreCompleto());
        p.setDni(dto.getDni());
        p.setCorreo(dto.getCorreo());
        p.setTelefono(dto.getTelefono());
        if (dto.getFechaNacimiento() != null && !dto.getFechaNacimiento().isEmpty()) {
            p.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        }
        return toDTO(pacienteRepository.save(p));
    }

    /** List all patients */
    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /** Get indications for the authenticated patient */
    public List<PruebaResponse> misIndicaciones() {
        String dni = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Orden> ordenes = ordenRepository.findByPacienteDniOrderByFechaEmisionDesc(dni);
        
        Set<Long> processedTestIds = new HashSet<>();
        List<PruebaResponse> indications = new ArrayList<>();

        for (Orden orden : ordenes) {
            // Solo considerar órdenes PENDIENTES o VIGENTES
            if ("PENDIENTE".equals(orden.getEstado()) || "VIGENTE".equals(orden.getEstado())) {
                for (PruebaLaboratorio prueba : orden.getPruebas()) {
                    if (processedTestIds.add(prueba.getId())) {
                        indications.add(pruebaService.toResponse(prueba));
                    }
                }
            }
        }
        return indications;
    }

    private PacienteDTO toDTO(Paciente p) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(p.getId());
        dto.setNombreCompleto(p.getNombreCompleto());
        dto.setDni(p.getDni());
        dto.setCorreo(p.getCorreo());
        dto.setTelefono(p.getTelefono());
        if (p.getFechaNacimiento() != null) {
            dto.setFechaNacimiento(p.getFechaNacimiento().toString());
        }
        return dto;
    }
}
