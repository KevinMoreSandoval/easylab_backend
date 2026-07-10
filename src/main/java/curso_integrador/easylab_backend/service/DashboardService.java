package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.DashboardResponse;
import curso_integrador.easylab_backend.repository.PruebaRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Servicio que calcula las estadísticas del dashboard de administración.
 *
 * Obtiene conteos reales de la base de datos para:
 *  - Usuarios activos
 *  - Pruebas disponibles (activas)
 *  - Reservas del mes (pendiente: entidad Reserva)
 *  - Próximas reservas (pendiente: entidad Reserva)
 */
@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final PruebaRepository pruebaRepository;

    public DashboardService(UsuarioRepository usuarioRepository, PruebaRepository pruebaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pruebaRepository = pruebaRepository;
    }

    /**
     * Obtiene las estadísticas del dashboard.
     * Nota: reservasDelMes y proximasReservas devuelven 0/vacío
     * hasta que se implemente la entidad Reserva.
     */
    public DashboardResponse getStats() {
        long usuariosActivos = usuarioRepository.countByActivoTrue();
        long pruebasDisponibles = pruebaRepository.countByActivaTrue();

        // Reservas: pendiente de implementación
        long reservasDelMes = 0;

        return new DashboardResponse(
                usuariosActivos,
                pruebasDisponibles,
                reservasDelMes,
                new ArrayList<>()  // Sin reservas por ahora
        );
    }
}
