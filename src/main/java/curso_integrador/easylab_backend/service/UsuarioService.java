package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.UsuarioRequest;
import curso_integrador.easylab_backend.dto.UsuarioResponse;
import curso_integrador.easylab_backend.model.Rol;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.RolRepository;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de gestión de usuarios para el módulo de administración.
 *
 * Responsabilidades:
 *  - Listar usuarios con su rol.
 *  - Crear usuarios con contraseña temporal hasheada.
 *  - Actualizar datos de usuario (nombre, rol, contraseña opcional).
 *  - Cambiar estado activo/inactivo (eliminación lógica).
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Lista todos los usuarios ordenados por fecha de creación descendente.
     */
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Crea un nuevo usuario con contraseña temporal hasheada.
     *
     * @throws IllegalArgumentException si el identificador ya existe o el rol no es válido.
     */
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        // Validar unicidad del identificador
        if (usuarioRepository.existsByIdentificador(request.getIdentificador().trim())) {
            throw new IllegalArgumentException("Ya existe un usuario con el identificador: " + request.getIdentificador());
        }

        // Buscar el rol
        Rol rol = rolRepository.findByNombre(request.getRolNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido: " + request.getRolNombre()));

        // Crear usuario con contraseña hasheada
        Usuario usuario = new Usuario(
                request.getIdentificador().trim(),
                passwordEncoder.encode(request.getPassword()),
                request.getNombreCompleto().trim(),
                rol
        );

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        Usuario saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    /**
     * Actualiza un usuario existente.
     * Si se envía password, se actualiza (rehashea). Si no, se mantiene.
     */
    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Verificar que el nuevo identificador no esté en uso por otro usuario
        if (!usuario.getIdentificador().equals(request.getIdentificador().trim())
                && usuarioRepository.existsByIdentificador(request.getIdentificador().trim())) {
            throw new IllegalArgumentException("Ya existe un usuario con el identificador: " + request.getIdentificador());
        }

        usuario.setIdentificador(request.getIdentificador().trim());
        usuario.setNombreCompleto(request.getNombreCompleto().trim());

        // Solo actualizar contraseña si se envió una nueva
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Actualizar rol
        Rol rol = rolRepository.findByNombre(request.getRolNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no válido: " + request.getRolNombre()));
        usuario.setRol(rol);

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        Usuario saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    /**
     * Cambia el estado activo/inactivo de un usuario.
     */
    public UsuarioResponse cambiarEstado(Long id, boolean activo) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(activo);
        Usuario saved = usuarioRepository.save(usuario);
        return toResponse(saved);
    }

    /**
     * Convierte entidad Usuario a DTO UsuarioResponse.
     */
    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getIdentificador(),
                usuario.getNombreCompleto(),
                usuario.getRol().getNombre(),
                usuario.getActivo(),
                usuario.getCreatedAt()
        );
    }
}
