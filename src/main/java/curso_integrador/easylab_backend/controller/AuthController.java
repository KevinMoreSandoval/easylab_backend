package curso_integrador.easylab_backend.controller;

import curso_integrador.easylab_backend.dto.LoginRequest;
import curso_integrador.easylab_backend.dto.LoginResponse;
import curso_integrador.easylab_backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para la autenticación de usuarios.
 *
 * Endpoint:
 *   POST /api/auth/login  →  Recibe credenciales, devuelve JWT + datos del usuario.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Autentica un usuario y devuelve un JWT.
     *
     * @param request Contiene userType, identifier y password.
     * @return LoginResponse con token, rol y nombre completo, o un error 401.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
