package curso_integrador.easylab_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Servicio encargado de generar y validar tokens JWT.
 *
 * Responsabilidades:
 *  - Generar un token con el identificador del usuario y su rol.
 *  - Extraer claims (identificador, rol) de un token válido.
 *  - Validar la expiración del token.
 */
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    /**
     * Genera un JWT con el identificador como subject y el rol como claim adicional.
     */
    public String generateToken(String identificador, String rol) {
        return Jwts.builder()
                .subject(identificador)
                .claims(Map.of("rol", rol))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extrae el identificador (subject) del token.
     */
    public String extractIdentificador(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extrae el rol almacenado en el token.
     */
    public String extractRol(String token) {
        return extractAllClaims(token).get("rol", String.class);
    }

    /**
     * Verifica si el token ha expirado.
     */
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
