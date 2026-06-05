package curso_integrador.easylab_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario del sistema.
 * El campo "identificador" varía según el rol:
 *   - PACIENTE:  DNI (8 dígitos)
 *   - MEDICO:    Código de colegiatura (5-6 dígitos)
 *   - RECEPCION: Correo institucional
 *   - ADMIN:     Correo institucional
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String identificador;

    @Column(nullable = false)
    private String password;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /* ---- Lifecycle callbacks ---- */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ---- Constructores ---- */

    public Usuario() {}

    public Usuario(String identificador, String password, String nombreCompleto, Rol rol) {
        this.identificador = identificador;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    /* ---- Getters & Setters ---- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIdentificador() { return identificador; }
    public void setIdentificador(String identificador) { this.identificador = identificador; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
