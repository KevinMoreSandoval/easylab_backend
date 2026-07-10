package curso_integrador.easylab_backend.dto;

import java.util.List;

/**
 * DTO para crear o actualizar una prueba de laboratorio.
 * Refleja todos los campos del formulario de CrearPrueba en el frontend.
 */
public class PruebaRequest {

    private String nombre;
    private String categoria;
    private String descripcion;
    private Boolean activa;

    // Indicaciones: Ayuno
    private Boolean requiereAyuno;
    private Integer horasAyuno;
    private Boolean ayunoAbsoluto;

    // Indicaciones: Agua
    private String consumoAgua;
    private String observacionesAgua;

    // Indicaciones: Medicamentos
    private Boolean suspenderMedicamentos;
    private String medicamentos;
    private Integer horasMedicamentos;

    // Restricciones
    private List<String> restricciones;
    private String otrasRestricciones;

    // Tipo de muestra
    private String tipoMuestra;
    private String otroTipoMuestra;

    // Requisitos de muestra
    private List<String> requisitosMuestra;
    private Integer tiempoMaximoMuestra;

    // Adicionales
    private String indicacionesAdicionales;
    private String videoUrl;

    /* ---- Constructores ---- */

    public PruebaRequest() {}

    /* ---- Getters & Setters ---- */

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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
}
