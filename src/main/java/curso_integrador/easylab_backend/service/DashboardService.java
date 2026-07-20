package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.DashboardResponse;
import curso_integrador.easylab_backend.model.Orden;
import curso_integrador.easylab_backend.repository.OrdenRepository;
import curso_integrador.easylab_backend.repository.PruebaRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que calcula las estadísticas del dashboard de administración.
 *
 * Obtiene conteos reales de la base de datos para:
 *  - Usuarios activos
 *  - Pruebas disponibles (activas)
 *  - Total de órdenes (se usan como "reservas del mes")
 *  - Últimas órdenes (se muestran como "próximas reservas")
 */
@Service
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final PruebaRepository pruebaRepository;
    private final OrdenRepository ordenRepository;

    public DashboardService(UsuarioRepository usuarioRepository,
                            PruebaRepository pruebaRepository,
                            OrdenRepository ordenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pruebaRepository = pruebaRepository;
        this.ordenRepository = ordenRepository;
    }

    /**
     * Obtiene las estadísticas del dashboard.
     */
    public DashboardResponse getStats() {
        long usuariosActivos = usuarioRepository.countByActivoTrue();
        long pruebasDisponibles = pruebaRepository.countByActivaTrue();
        long reservasDelMes = ordenRepository.count();

        List<Orden> ultimasOrdenes = ordenRepository.findAllByOrderByFechaEmisionDesc()
                .stream().limit(5).collect(Collectors.toList());

        List<DashboardResponse.ReservaResumen> proximasReservas = ultimasOrdenes.stream()
                .map(o -> new DashboardResponse.ReservaResumen(
                        o.getPaciente().getNombreCompleto(),
                        o.getPruebas().stream()
                                .map(p -> p.getNombre())
                                .collect(Collectors.joining(", ")),
                        o.getFechaEmision().toString(),
                        o.getEstado()
                ))
                .collect(Collectors.toList());

        return new DashboardResponse(
                usuariosActivos,
                pruebasDisponibles,
                reservasDelMes,
                proximasReservas
        );
    }
}
