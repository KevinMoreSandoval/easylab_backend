package curso_integrador.easylab_backend.service;

import curso_integrador.easylab_backend.dto.LoginRequest;
import curso_integrador.easylab_backend.dto.LoginResponse;
import curso_integrador.easylab_backend.model.Usuario;
import curso_integrador.easylab_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Servicio de autenticación.
 *
 * Responsabilidades:
 *  - Mapear el tipo de usuario del frontend al nombre del rol en BD.
 *  - Buscar al usuario por identificador.
 *  - Validar contraseña y que el rol coincida con el tab seleccionado.
 *  - Devolver un LoginResponse con el JWT generado.
 */
@Service
public class AuthService {

    /** Mapeo: nombre del tab en el frontend → nombre del rol en la BD */
    private static final Map<String, String> TAB_TO_ROL = Map.of(
            "Paciente",  "PACIENTE",
            "Médico",    "MEDICO",
            "Recepción", "RECEPCION"
    );

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Autentica al usuario y retorna un JWT + datos básicos.
     *
     * @throws IllegalArgumentException si el tipo de usuario no es válido.
     * @throws RuntimeException si las credenciales son incorrectas.
     */
    public LoginResponse authenticate(LoginRequest request) {
        // 1. Validar que el tab enviado sea válido
        String rolEsperado = TAB_TO_ROL.get(request.getUserType());
        if (rolEsperado == null) {
            throw new IllegalArgumentException("Tipo de usuario no válido: " + request.getUserType());
        }

        // 2. Buscar usuario por identificador
        Usuario usuario = usuarioRepository
                .findByIdentificador(request.getIdentifier().trim())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas."));

        // 3. Verificar que el usuario esté activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada. Contacta al administrador.");
        }

        // 4. Verificar que el rol del usuario coincida con el tab seleccionado
        if (!usuario.getRol().getNombre().equals(rolEsperado)) {
            throw new RuntimeException("Credenciales incorrectas.");
        }

        // 5. Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas.");
        }

        // 6. Generar JWT
        String token = jwtService.generateToken(
                usuario.getIdentificador(),
                usuario.getRol().getNombre()
        );

        return new LoginResponse(token, usuario.getRol().getNombre(), usuario.getNombreCompleto());
    }
}
