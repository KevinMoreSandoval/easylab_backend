package curso_integrador.easylab_backend.dto;

import java.util.List;

/**
 * DTO for creating a new Orden (medical order).
 */
public class OrdenRequest {
    private String pacienteDni;       // DNI of the patient
    private List<Long> pruebaIds;     // IDs of selected PruebaLaboratorio
    private String fechaEmision;      // ISO date: yyyy-MM-dd

    public OrdenRequest() {}

    public String getPacienteDni() { return pacienteDni; }
    public void setPacienteDni(String pacienteDni) { this.pacienteDni = pacienteDni; }

    public List<Long> getPruebaIds() { return pruebaIds; }
    public void setPruebaIds(List<Long> pruebaIds) { this.pruebaIds = pruebaIds; }

    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }
}
