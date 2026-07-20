package curso_integrador.easylab_backend.dto;

/**
 * DTO para la creación de una cita médica.
 */
public class CitaRequest {

    private String pacienteDni;   // DNI del paciente
    private Long medicoId;        // ID del usuario médico
    private String fecha;         // ISO date: yyyy-MM-dd
    private String hora;          // ISO time: HH:mm

    public CitaRequest() {}

    public String getPacienteDni() { return pacienteDni; }
    public void setPacienteDni(String pacienteDni) { this.pacienteDni = pacienteDni; }

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
}
