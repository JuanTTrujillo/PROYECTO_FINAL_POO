package co.edu.uniquindio;

import java.time.LocalDate;



public record Bloqueo(LocalDate fechaInicio, LocalDate fechaFin, String motivo) {

    public boolean chocaCon(LocalDate entrada, LocalDate salida) {
        if (fechaInicio.isBefore(salida)) {      // ¿el bloqueo empieza antes de que el huesped se vaya?
            if (entrada.isBefore(fechaFin)) {    // ¿llega antes de que el bloqueo termine?
                return true;                     // sí a las dos: se cruzan, esto significa que interfiere y genera el bloqueo
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
