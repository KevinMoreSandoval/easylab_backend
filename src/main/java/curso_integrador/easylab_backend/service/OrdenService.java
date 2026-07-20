package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.OrdenRequest;
import curso_integrador.easylab_backend.dto.OrdenResponse;
import curso_integrador.easylab_backend.model.Orden;
import curso_integrador.easylab_backend.model.Paciente;
import curso_integrador.easylab_backend.model.PruebaLaboratorio;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.OrdenRepository;
import curso_integrador.easylab_backend.repository.PacienteRepository;
import curso_integrador.easylab_backend.repository.PruebaRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final PacienteRepository pacienteRepository;
    private final PruebaRepository pruebaRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdenService(OrdenRepository ordenRepository,
                        PacienteRepository pacienteRepository,
                        PruebaRepository pruebaRepository,
                        UsuarioRepository usuarioRepository) {
        this.ordenRepository = ordenRepository;
        this.pacienteRepository = pacienteRepository;
        this.pruebaRepository = pruebaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** Create a new medical order */
    public OrdenResponse create(OrdenRequest req) {
        // Get the authenticated doctor
        String identificador = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario medico = usuarioRepository.findByIdentificador(identificador)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // Find patient by DNI
        Paciente paciente = pacienteRepository.findByDni(req.getPacienteDni())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + req.getPacienteDni()));

        // Find selected tests
        Set<PruebaLaboratorio> pruebas = new HashSet<>();
        if (req.getPruebaIds() != null) {
            for (Long pruebaId : req.getPruebaIds()) {
                PruebaLaboratorio prueba = pruebaRepository.findById(pruebaId)
                        .orElseThrow(() -> new RuntimeException("Prueba no encontrada con id: " + pruebaId));
                pruebas.add(prueba);
            }
        }

        // Build the order
        Orden orden = new Orden();
        long count = ordenRepository.count() + 1;
        orden.setNumeroOrden(String.format("ORD-%03d", count));
        orden.setPaciente(paciente);
        orden.setMedico(medico);
        orden.setFechaEmision(LocalDate.parse(req.getFechaEmision()));
        orden.setEstado("PENDIENTE");
        orden.setPruebas(pruebas);

        Orden saved = ordenRepository.save(orden);
        return toResponse(saved);
    }

    /** Get all orders */
    public List<OrdenResponse> findAll() {
        return ordenRepository.findAllByOrderByFechaEmisionDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /** Get orders by estado */
    public List<OrdenResponse> findByEstado(String estado) {
        return ordenRepository.findByEstado(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private OrdenResponse toResponse(Orden o) {
        OrdenResponse r = new OrdenResponse();
        r.setId(o.getId());
        r.setNumeroOrden(o.getNumeroOrden());
        r.setPaciente(o.getPaciente().getNombreCompleto());
        r.setPacienteDni(o.getPaciente().getDni());
        r.setMedico(o.getMedico().getNombreCompleto());
        r.setFechaEmision(o.getFechaEmision().toString());
        r.setEstado(o.getEstado());
        r.setPruebas(o.getPruebas().stream()
                .map(PruebaLaboratorio::getNombre)
                .collect(Collectors.toList()));
        return r;
    }
}
