package co.edu.uniquindio;

import java.time.LocalDate;

public class DetalleNoche {

    private LocalDate noche;
    private TipoTemporada temporada;
    private int tarifa;
    private int personasQuePagan;
    private int subtotal;

    public DetalleNoche(LocalDate noche, TipoTemporada temporada,
                        int tarifa, int personasQuePagan) {
        this.noche = noche;
        this.temporada = temporada;
        this.tarifa = tarifa;
        this.personasQuePagan = personasQuePagan;
        this.subtotal = calcularSubtotal();
    }

    // Calcular el subtotal de la noche
    public int calcularSubtotal() {
        return tarifa * personasQuePagan;
    }

    public LocalDate getNoche() {
        return noche;
    }

    public TipoTemporada getTemporada() {
        return temporada;
    }

    public int getTarifa() {
        return tarifa;
    }

    public int getPersonasQuePagan() {
        return personasQuePagan;
    }

    public int getSubtotal() {
        return subtotal;
    }
}