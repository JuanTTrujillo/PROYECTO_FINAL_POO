package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cotizacion {

    private Apartamento apartamento;
    private LocalDate entrada;
    private LocalDate salida;
    private ArrayList<DetalleNoche> detalle;
    private int total;

    public Cotizacion(Apartamento apartamento, LocalDate entrada, LocalDate salida) {
        this.apartamento = apartamento;
        this.entrada = entrada;
        this.salida = salida;
        this.detalle = new ArrayList<>();
        this.total = 0;
    }

    public void agregarNoche(DetalleNoche noche) {
        detalle.add(noche);
        total += noche.subtotal();
    }

    public int getCantidadNoches() {
        return detalle.size();
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public LocalDate getEntrada() {
        return entrada;
    }

    public LocalDate getSalida() {
        return salida;
    }

    public ArrayList<DetalleNoche> getDetalle() {
        return detalle;
    }

    public int getTotal() {
        return total;
    }
}