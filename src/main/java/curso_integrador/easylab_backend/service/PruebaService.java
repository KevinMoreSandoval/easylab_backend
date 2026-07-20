package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.PruebaRequest;
import curso_integrador.easylab_backend.dto.PruebaResponse;
import curso_integrador.easylab_backend.model.PruebaLaboratorio;
import curso_integrador.easylab_backend.repository.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servicio de gestión de pruebas de laboratorio.
 *
 * Responsabilidades:
 *  - Listar todas las pruebas.
 *  - Crear prueba con código interno auto-generado (HEM-001, BIO-004, etc.).
 *  - Actualizar prueba existente.
 *  - Obtener una prueba por ID.
 */
@Service
public class PruebaService {

    /**
     * Mapeo de categoría completa → prefijo de 3 letras para el código interno.
     */
    private static final Map<String, String> CATEGORIA_PREFIJO = Map.of(
            "Bioquímica",     "BIO",
            "Hematología",    "HEM",
            "Microbiología",  "MIC",
            "Inmunología",    "INM"
    );

    private final PruebaRepository pruebaRepository;

    public PruebaService(PruebaRepository pruebaRepository) {
        this.pruebaRepository = pruebaRepository;
    }

    /**
     * Lista todas las pruebas ordenadas por fecha de creación descendente.
     */
    public List<PruebaResponse> listarPruebas() {
        return pruebaRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene una prueba por su ID.
     */
    public PruebaResponse obtenerPrueba(Long id) {
        PruebaLaboratorio prueba = pruebaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada con ID: " + id));
        return toResponse(prueba);
    }

    /**
     * Crea una nueva prueba con código interno auto-generado.
     * El código se genera con el formato: PREFIJO-NNN (ej: HEM-003).
     */
    public PruebaResponse crearPrueba(PruebaRequest request) {
        PruebaLaboratorio prueba = new PruebaLaboratorio();
        mapRequestToEntity(request, prueba);

        // Generar código interno automáticamente
        String codigo = generarCodigo(request.getCategoria());
        prueba.setCodigo(codigo);

        PruebaLaboratorio saved = pruebaRepository.save(prueba);
        return toResponse(saved);
    }

    /**
     * Actualiza una prueba existente. No cambia el código interno.
     */
    public PruebaResponse actualizarPrueba(Long id, PruebaRequest request) {
        PruebaLaboratorio prueba = pruebaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prueba no encontrada con ID: " + id));

        mapRequestToEntity(request, prueba);
        // No se cambia el código interno al actualizar

        PruebaLaboratorio saved = pruebaRepository.save(prueba);
        return toResponse(saved);
    }

    /**
     * Genera un código interno único basado en la categoría.
     * Formato: PREFIJO-NNN (ej: BIO-001, HEM-003)
     */
    private String generarCodigo(String categoria) {
        String prefijo = CATEGORIA_PREFIJO.getOrDefault(categoria, categoria.substring(0, 3).toUpperCase());

        long count = pruebaRepository.countByCategoriaIgnoreCase(categoria);
        String codigo;
        do {
            count++;
            codigo = prefijo + "-" + String.format("%03d", count);
        } while (pruebaRepository.existsByCodigo(codigo));

        return codigo;
    }

    /**
     * Mapea campos del DTO request a la entidad.
     */
    private void mapRequestToEntity(PruebaRequest request, PruebaLaboratorio entity) {
        entity.setNombre(request.getNombre());
        entity.setCategoria(request.getCategoria());
        entity.setDescripcion(request.getDescripcion());
        entity.setActiva(request.getActiva() != null ? request.getActiva() : true);

        // Ayuno
        entity.setRequiereAyuno(request.getRequiereAyuno() != null ? request.getRequiereAyuno() : false);
        entity.setHorasAyuno(request.getHorasAyuno());
        entity.setAyunoAbsoluto(request.getAyunoAbsoluto() != null ? request.getAyunoAbsoluto() : false);

        // Agua
        entity.setConsumoAgua(request.getConsumoAgua() != null ? request.getConsumoAgua() : "Permitido");
        entity.setObservacionesAgua(request.getObservacionesAgua());

        // Medicamentos
        entity.setSuspenderMedicamentos(request.getSuspenderMedicamentos() != null ? request.getSuspenderMedicamentos() : false);
        entity.setMedicamentos(request.getMedicamentos());
        entity.setHorasMedicamentos(request.getHorasMedicamentos());

        // Restricciones
        entity.setRestricciones(request.getRestricciones() != null ? request.getRestricciones() : new ArrayList<>());
        entity.setOtrasRestricciones(request.getOtrasRestricciones());

        // Muestra
        entity.setTipoMuestra(request.getTipoMuestra());
        entity.setOtroTipoMuestra(request.getOtroTipoMuestra());
        entity.setRequisitosMuestra(request.getRequisitosMuestra() != null ? request.getRequisitosMuestra() : new ArrayList<>());
        entity.setTiempoMaximoMuestra(request.getTiempoMaximoMuestra());

        // Adicionales
        entity.setIndicacionesAdicionales(request.getIndicacionesAdicionales());
        entity.setVideoUrl(request.getVideoUrl());
    }

    /**
     * Convierte entidad a DTO response.
     */
    public PruebaResponse toResponse(PruebaLaboratorio entity) {
        PruebaResponse response = new PruebaResponse();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setCodigo(entity.getCodigo());
        response.setCategoria(entity.getCategoria());
        response.setDescripcion(entity.getDescripcion());
        response.setActiva(entity.getActiva());
        response.setRequiereAyuno(entity.getRequiereAyuno());
        response.setHorasAyuno(entity.getHorasAyuno());
        response.setAyunoAbsoluto(entity.getAyunoAbsoluto());
        response.setConsumoAgua(entity.getConsumoAgua());
        response.setObservacionesAgua(entity.getObservacionesAgua());
        response.setSuspenderMedicamentos(entity.getSuspenderMedicamentos());
        response.setMedicamentos(entity.getMedicamentos());
        response.setHorasMedicamentos(entity.getHorasMedicamentos());
        response.setRestricciones(entity.getRestricciones());
        response.setOtrasRestricciones(entity.getOtrasRestricciones());
        response.setTipoMuestra(entity.getTipoMuestra());
        response.setOtroTipoMuestra(entity.getOtroTipoMuestra());
        response.setRequisitosMuestra(entity.getRequisitosMuestra());
        response.setTiempoMaximoMuestra(entity.getTiempoMaximoMuestra());
        response.setIndicacionesAdicionales(entity.getIndicacionesAdicionales());
        response.setVideoUrl(entity.getVideoUrl());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}
