package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.PacienteDTO;
import curso_integrador.easylab_backend.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Paciente operations.
 *
 * Endpoints:
 *   GET  /api/pacientes          → List all patients
 *   GET  /api/pacientes/dni/{dni} → Find patient by DNI
 *   POST /api/pacientes          → Register a new patient
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni) {
        try {
            return ResponseEntity.ok(pacienteService.findByDni(dni));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PacienteDTO dto) {
        try {
            return ResponseEntity.status(201).body(pacienteService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
