package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.OrdenRequest;
import curso_integrador.easylab_backend.dto.OrdenResponse;
import curso_integrador.easylab_backend.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Orden (medical orders).
 *
 * Endpoints:
 *   GET  /api/ordenes       → List all orders
 *   POST /api/ordenes       → Create a new order (MEDICO role)
 */
@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenResponse>> listAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrdenRequest req) {
        try {
            return ResponseEntity.status(201).body(ordenService.create(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
