package curso_integrador.easylab_backend.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de usuario (sin password).
 */
public class UsuarioResponse {

    private Long id;
    private String identificador;
    private String nombreCompleto;
    private String rolNombre;
    private Boolean activo;
    private LocalDateTime createdAt;

    /* ---- Constructores ---- */

    public UsuarioResponse() {}

    public UsuarioResponse(Long id, String identificador, String nombreCompleto, String rolNombre, Boolean activo, LocalDateTime createdAt) {
        this.id = id;
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.rolNombre = rolNombre;
        this.activo = activo;
        this.createdAt = createdAt;
    }

    /* ---- Getters & Setters ---- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
