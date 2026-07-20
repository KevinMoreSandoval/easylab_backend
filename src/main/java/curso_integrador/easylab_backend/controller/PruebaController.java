package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.PruebaRequest;
import curso_integrador.easylab_backend.dto.PruebaResponse;
import curso_integrador.easylab_backend.service.PruebaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión del catálogo de pruebas de laboratorio.
 *
 * Endpoints:
 *   GET    /api/admin/pruebas         → Listar todas las pruebas
 *   GET    /api/admin/pruebas/{id}    → Obtener una prueba
 *   POST   /api/admin/pruebas         → Crear una nueva prueba
 *   PUT    /api/admin/pruebas/{id}    → Actualizar una prueba
 */
@RestController
@RequestMapping("/api/admin/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;

    public PruebaController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping
    public ResponseEntity<List<PruebaResponse>> listar() {
        return ResponseEntity.ok(pruebaService.listarPruebas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            PruebaResponse response = pruebaService.obtenerPrueba(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PruebaRequest request) {
        try {
            PruebaResponse response = pruebaService.crearPrueba(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody PruebaRequest request) {
        try {
            PruebaResponse response = pruebaService.actualizarPrueba(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
