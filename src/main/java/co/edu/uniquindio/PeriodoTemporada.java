package co.edu.uniquindio;
import java.time.LocalDate;

public record PeriodoTemporada(
        TipoTemporada tipoTemporada,
        LocalDate fechaInicio,
        LocalDate fechaFin
) {
}