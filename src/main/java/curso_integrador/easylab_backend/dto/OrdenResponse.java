package curso_integrador.easylab_backend.dto;

import java.util.List;

/**
 * DTO for returning Orden data to the frontend.
 */
public class OrdenResponse {
    private Long id;
    private String numeroOrden;
    private String paciente;
    private String pacienteDni;
    private String medico;
    private String fechaEmision;
    private String estado;
    private List<String> pruebas;

    public OrdenResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroOrden() { return numeroOrden; }
    public void setNumeroOrden(String numeroOrden) { this.numeroOrden = numeroOrden; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getPacienteDni() { return pacienteDni; }
    public void setPacienteDni(String pacienteDni) { this.pacienteDni = pacienteDni; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<String> getPruebas() { return pruebas; }
    public void setPruebas(List<String> pruebas) { this.pruebas = pruebas; }
}
