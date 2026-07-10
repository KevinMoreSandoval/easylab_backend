package curso_integrador.easylab_backend.dto;

/**
 * DTO para crear o actualizar un usuario desde el panel de administración.
 */
public class UsuarioRequest {

    private String identificador;
    private String password;
    private String nombreCompleto;
    private String rolNombre;  // "ADMIN", "MEDICO", "RECEPCION"
    private Boolean activo;

    /* ---- Constructores ---- */

    public UsuarioRequest() {}

    public UsuarioRequest(String identificador, String password, String nombreCompleto, String rolNombre, Boolean activo) {
        this.identificador = identificador;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rolNombre = rolNombre;
        this.activo = activo;
    }

    /* ---- Getters & Setters ---- */

    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
