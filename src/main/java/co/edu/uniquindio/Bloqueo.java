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
}
