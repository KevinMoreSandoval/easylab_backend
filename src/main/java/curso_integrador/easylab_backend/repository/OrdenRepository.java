package curso_integrador.easylab_backend.repository;

import curso_integrador.easylab_backend.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findAllByOrderByFechaEmisionDesc();
    List<Orden> findByEstado(String estado);
    List<Orden> findByPacienteDniOrderByFechaEmisionDesc(String dni);
    long countByEstado(String estado);
}
