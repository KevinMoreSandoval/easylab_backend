package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.DashboardResponse;
import curso_integrador.easylab_backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para las estadísticas del dashboard de administración.
 *
 * Endpoint:
 *   GET /api/admin/dashboard → Estadísticas generales del sistema
 */
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getStats() {
        return ResponseEntity.ok(dashboardService.getStats());
    }
}
