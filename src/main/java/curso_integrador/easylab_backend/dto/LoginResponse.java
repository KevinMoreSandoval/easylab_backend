package curso_integrador.easylab_backend.dto;

/**
 * DTO para la respuesta de login exitoso.
 * Devuelve el JWT, el rol del usuario y su nombre completo
 * para que el frontend redirija al dashboard correcto.
 */
public class LoginResponse {

    private String token;
    private String rol;
    private String nombreCompleto;

    /* ---- Constructores ---- */

    public LoginResponse() {}

    public LoginResponse(String token, String rol, String nombreCompleto) {
        this.token = token;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
    }

    /* ---- Getters & Setters ---- */

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}
