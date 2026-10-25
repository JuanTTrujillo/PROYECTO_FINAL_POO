package co.edu.uniquindio;

import java.time.LocalDate;

public record Bloqueo() {
    private  LocalDate fechaInicio;
    private  LocalDate fechaFin;
    private  String motivo;


    public boolean interfiereCon( LocalDate fechaInicio, LocalDate fechaFin){
        return entrada.isbefore(fechaFin) &&  && salida.isAfter(fechaInicio);
    }

}
