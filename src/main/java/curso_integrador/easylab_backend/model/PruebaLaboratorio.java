package curso_integrador.easylab_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa una prueba de laboratorio en el catálogo.
 *
 * Contiene toda la información necesaria para que el paciente
 * conozca las indicaciones, restricciones y requisitos de muestra.
 */
@Entity
@Table(name = "pruebas_laboratorio")
public class PruebaLaboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    /** Código interno auto-generado (ej: HEM-001, BIO-004) */
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Boolean activa = true;

    /* ---- Indicaciones: Ayuno ---- */

    @Column(name = "requiere_ayuno", nullable = false)
    private Boolean requiereAyuno = false;

    @Column(name = "horas_ayuno")
    private Integer horasAyuno;

    @Column(name = "ayuno_absoluto", nullable = false)
    private Boolean ayunoAbsoluto = false;

    /* ---- Indicaciones: Agua ---- */

    @Column(name = "consumo_agua", length = 20)
    private String consumoAgua = "Permitido";

    @Column(name = "observaciones_agua")
    private String observacionesAgua;

    /* ---- Indicaciones: Medicamentos ---- */

    @Column(name = "suspender_medicamentos", nullable = false)
    private Boolean suspenderMedicamentos = false;

    @Column(length = 500)
    private String medicamentos;

    @Column(name = "horas_medicamentos")
    private Integer horasMedicamentos;

    /* ---- Restricciones ---- */

    @ElementCollection
    @CollectionTable(name = "prueba_restricciones", joinColumns = @JoinColumn(name = "prueba_id"))
    @Column(name = "restriccion")
    private List<String> restricciones = new ArrayList<>();

    @Column(name = "otras_restricciones", columnDefinition = "TEXT")
    private String otrasRestricciones;

    /* ---- Tipo de muestra ---- */

    @Column(name = "tipo_muestra", length = 50)
    private String tipoMuestra;

    @Column(name = "otro_tipo_muestra", length = 200)
    private String otroTipoMuestra;

    /* ---- Requisitos de muestra ---- */

    @ElementCollection
    @CollectionTable(name = "prueba_requisitos_muestra", joinColumns = @JoinColumn(name = "prueba_id"))
    @Column(name = "requisito")
    private List<String> requisitosMuestra = new ArrayList<>();

    @Column(name = "tiempo_maximo_muestra")
    private Integer tiempoMaximoMuestra;

    /* ---- Adicionales ---- */

    @Column(name = "indicaciones_adicionales", columnDefinition = "TEXT")
    private String indicacionesAdicionales;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /* ---- Lifecycle callbacks ---- */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ---- Constructores ---- */

    public PruebaLaboratorio() {}

    /* ---- Getters & Setters ---- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }

    public Boolean getRequiereAyuno() { return requiereAyuno; }
    public void setRequiereAyuno(Boolean requiereAyuno) { this.requiereAyuno = requiereAyuno; }

    public Integer getHorasAyuno() { return horasAyuno; }
    public void setHorasAyuno(Integer horasAyuno) { this.horasAyuno = horasAyuno; }

    public Boolean getAyunoAbsoluto() { return ayunoAbsoluto; }
    public void setAyunoAbsoluto(Boolean ayunoAbsoluto) { this.ayunoAbsoluto = ayunoAbsoluto; }

    public String getConsumoAgua() { return consumoAgua; }
    public void setConsumoAgua(String consumoAgua) { this.consumoAgua = consumoAgua; }

    public String getObservacionesAgua() { return observacionesAgua; }
    public void setObservacionesAgua(String observacionesAgua) { this.observacionesAgua = observacionesAgua; }

    public Boolean getSuspenderMedicamentos() { return suspenderMedicamentos; }
    public void setSuspenderMedicamentos(Boolean suspenderMedicamentos) { this.suspenderMedicamentos = suspenderMedicamentos; }

    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }

    public Integer getHorasMedicamentos() { return horasMedicamentos; }
    public void setHorasMedicamentos(Integer horasMedicamentos) { this.horasMedicamentos = horasMedicamentos; }

    public List<String> getRestricciones() { return restricciones; }
    public void setRestricciones(List<String> restricciones) { this.restricciones = restricciones; }

    public String getOtrasRestricciones() { return otrasRestricciones; }
    public void setOtrasRestricciones(String otrasRestricciones) { this.otrasRestricciones = otrasRestricciones; }

    public String getTipoMuestra() { return tipoMuestra; }
    public void setTipoMuestra(String tipoMuestra) { this.tipoMuestra = tipoMuestra; }

    public String getOtroTipoMuestra() { return otroTipoMuestra; }
    public void setOtroTipoMuestra(String otroTipoMuestra) { this.otroTipoMuestra = otroTipoMuestra; }

    public List<String> getRequisitosMuestra() { return requisitosMuestra; }
    public void setRequisitosMuestra(List<String> requisitosMuestra) { this.requisitosMuestra = requisitosMuestra; }

    public Integer getTiempoMaximoMuestra() { return tiempoMaximoMuestra; }
    public void setTiempoMaximoMuestra(Integer tiempoMaximoMuestra) { this.tiempoMaximoMuestra = tiempoMaximoMuestra; }

    public String getIndicacionesAdicionales() { return indicacionesAdicionales; }
    public void setIndicacionesAdicionales(String indicacionesAdicionales) { this.indicacionesAdicionales = indicacionesAdicionales; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
