package curso_integrador.easylab_backend.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un rol del sistema.
 * Roles válidos: PACIENTE, MEDICO, RECEPCION, ADMIN.
 */
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nombre;

    /* ---- Constructores ---- */

    public Rol() {}

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    /* ---- Getters & Setters ---- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
