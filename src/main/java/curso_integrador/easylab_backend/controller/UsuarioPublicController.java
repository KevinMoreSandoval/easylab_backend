package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para consultas públicas de usuarios.
 *
 * Endpoint:
 *   GET /api/usuarios/medicos → Lista de médicos activos (para selector de recepción)
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioPublicController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioPublicController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/medicos")
    public ResponseEntity<List<Map<String, Object>>> listarMedicos() {
        List<Map<String, Object>> medicos = usuarioRepository.findAll().stream()
                .filter(u -> u.getRol().getNombre().equals("MEDICO") && u.getActivo())
                .map(u -> Map.<String, Object>of(
                        "id", u.getId(),
                        "nombreCompleto", u.getNombreCompleto(),
                        "identificador", u.getIdentificador()
                ))
                .toList();
        return ResponseEntity.ok(medicos);
    }
}
