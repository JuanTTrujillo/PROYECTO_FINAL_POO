package co.edu.uniquindio;

import java.time.LocalDate;


public record DetalleNoche(LocalDate noche, TipoTemporada temporada,
                           int tarifa, int personasQuePagan) {

    // El subtotal se calcula, no se guarda
    public int calcularSubtotal() {
        return tarifa * personasQuePagan;
    }
}