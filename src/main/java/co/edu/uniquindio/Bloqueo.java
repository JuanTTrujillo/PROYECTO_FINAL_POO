package co.edu.uniquindio;

import java.time.LocalDate;



public record Bloqueo(LocalDate fechaInicio, LocalDate fechaFin, String motivo) {

    public boolean interfiereCon(LocalDate entrada, LocalDate salida) {
        if (fechaInicio.isBefore(salida)) {
            if (entrada.isBefore(fechaFin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public LocalDate fechaInicio() {
        return fechaInicio;
    }

    @Override
    public LocalDate fechaFin() {
        return fechaFin;
    }

    @Override
    public String motivo() {
        return motivo;
    }
}
