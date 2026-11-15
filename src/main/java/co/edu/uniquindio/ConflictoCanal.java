package co.edu.uniquindio;

import java.time.LocalDate;

public record ConflictoCanal(
        String codigoExterno,
        String motivo,
        LocalDate fechaRegistro
) {
}