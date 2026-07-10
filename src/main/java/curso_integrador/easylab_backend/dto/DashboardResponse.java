package curso_integrador.easylab_backend.dto;

import java.util.List;

/**
 * DTO para la respuesta del dashboard de administración.
 * Contiene las estadísticas generales del sistema.
 */
public class DashboardResponse {

    private long usuariosActivos;
    private long pruebasDisponibles;
    private long reservasDelMes;
    private List<ReservaResumen> proximasReservas;

    /* ---- Constructores ---- */

    public DashboardResponse() {}

    public DashboardResponse(long usuariosActivos, long pruebasDisponibles, long reservasDelMes, List<ReservaResumen> proximasReservas) {
        this.usuariosActivos = usuariosActivos;
        this.pruebasDisponibles = pruebasDisponibles;
        this.reservasDelMes = reservasDelMes;
        this.proximasReservas = proximasReservas;
    }

    /* ---- Getters & Setters ---- */

    public long getUsuariosActivos() { return usuariosActivos; }
    public void setUsuariosActivos(long usuariosActivos) { this.usuariosActivos = usuariosActivos; }

    public long getPruebasDisponibles() { return pruebasDisponibles; }
    public void setPruebasDisponibles(long pruebasDisponibles) { this.pruebasDisponibles = pruebasDisponibles; }

    public long getReservasDelMes() { return reservasDelMes; }
    public void setReservasDelMes(long reservasDelMes) { this.reservasDelMes = reservasDelMes; }

    public List<ReservaResumen> getProximasReservas() { return proximasReservas; }
    public void setProximasReservas(List<ReservaResumen> proximasReservas) { this.proximasReservas = proximasReservas; }

    /**
     * Resumen de una reserva para la tabla del dashboard.
     */
    public static class ReservaResumen {
        private String paciente;
        private String prueba;
        private String fecha;
        private String estado;

        public ReservaResumen() {}

        public ReservaResumen(String paciente, String prueba, String fecha, String estado) {
            this.paciente = paciente;
            this.prueba = prueba;
            this.fecha = fecha;
            this.estado = estado;
        }

        public String getPaciente() { return paciente; }
        public void setPaciente(String paciente) { this.paciente = paciente; }

        public String getPrueba() { return prueba; }
        public void setPrueba(String prueba) { this.prueba = prueba; }

        public String getFecha() { return fecha; }
        public void setFecha(String fecha) { this.fecha = fecha; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
    }
}
