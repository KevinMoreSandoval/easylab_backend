package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.PruebaResponse;
import curso_integrador.easylab_backend.service.PruebaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de solo lectura para las pruebas de laboratorio.
 *
 * Este endpoint está disponible para todos los usuarios autenticados
 * (médicos para crear órdenes, pacientes para ver indicaciones).
 *
 * Endpoint:
 *   GET /api/pruebas → Listar todas las pruebas activas
 */
@RestController
@RequestMapping("/api/pruebas")
public class PruebaPublicController {

    private final PruebaService pruebaService;

    public PruebaPublicController(PruebaService pruebaService) {
        this.pruebaService = pruebaService;
    }

    @GetMapping
    public ResponseEntity<List<PruebaResponse>> listarActivas() {
        return ResponseEntity.ok(pruebaService.listarPruebas());
    }
}
