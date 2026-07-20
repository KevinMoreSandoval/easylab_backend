package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.CitaRequest;
import curso_integrador.easylab_backend.dto.CitaResponse;
import curso_integrador.easylab_backend.service.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de citas médicas.
 *
 * Endpoints:
 *   POST  /api/citas                    → Agendar una cita
 *   GET   /api/citas                    → Listar todas las citas
 *   GET   /api/citas/medico/pendientes  → Citas pendientes del médico logueado
 *   PATCH /api/citas/{id}/estado        → Cambiar estado de una cita
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CitaRequest req) {
        try {
            CitaResponse response = citaService.crear(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<CitaResponse>> listarTodas() {
        return ResponseEntity.ok(citaService.listarTodas());
    }

    @GetMapping("/medico/pendientes")
    public ResponseEntity<List<CitaResponse>> pendientesMedico() {
        return ResponseEntity.ok(citaService.citasPendientesMedico());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            if (nuevoEstado == null || nuevoEstado.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El campo 'estado' es requerido."));
            }
            CitaResponse response = citaService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
