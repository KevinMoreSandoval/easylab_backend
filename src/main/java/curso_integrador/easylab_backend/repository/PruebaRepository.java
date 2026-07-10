package curso_integrador.easylab_backend.repository;

import curso_integrador.easylab_backend.model.PruebaLaboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad PruebaLaboratorio.
 */
public interface PruebaRepository extends JpaRepository<PruebaLaboratorio, Long> {

    List<PruebaLaboratorio> findByActivaTrue();

    long countByActivaTrue();

    long countByCategoriaIgnoreCase(String categoria);

    boolean existsByCodigo(String codigo);

    List<PruebaLaboratorio> findAllByOrderByCreatedAtDesc();
}
