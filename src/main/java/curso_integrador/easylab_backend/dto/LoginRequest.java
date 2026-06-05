package curso_integrador.easylab_backend.dto;

/**
 * DTO para la petición de login.
 * Recibe el tipo de usuario (pestaña seleccionada),
 * el identificador (DNI, colegiatura o email) y la contraseña.
 */
public class LoginRequest {

    private String userType;     // "Paciente", "Médico", "Recepción"
    private String identifier;   // DNI, código colegiatura, o email
    private String password;

    /* ---- Constructores ---- */

    public LoginRequest() {}

    public LoginRequest(String userType, String identifier, String password) {
        this.userType = userType;
        this.identifier = identifier;
        this.password = password;
    }

    /* ---- Getters & Setters ---- */

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
