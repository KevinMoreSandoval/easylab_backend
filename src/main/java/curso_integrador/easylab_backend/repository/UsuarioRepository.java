package curso_integrador.easylab_backend.repository;

import curso_integrador.easylab_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByIdentificador(String identificador);

    boolean existsByIdentificador(String identificador);

    long countByActivoTrue();

    List<Usuario> findAllByOrderByCreatedAtDesc();
}
