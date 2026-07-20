package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.PacienteDTO;
import curso_integrador.easylab_backend.model.Paciente;
import curso_integrador.easylab_backend.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
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
