package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservaDirecta extends Reserva {

    private Recepcionista registradaPor;

    public ReservaDirecta(String codigo, Apartamento apartamento, Huesped titular, ArrayList<Ocupante> ocupantes,
                          LocalDate fechaEntrada, LocalDate fechaSalida, LocalDateTime fechaCreacion,
                          int valorEstancia, PoliticaCancelacion politica, Recepcionista registradaPor) {
        super(codigo, apartamento, titular, ocupantes, fechaEntrada, fechaSalida, fechaCreacion, valorEstancia, politica);
        this.registradaPor = registradaPor;
    }

    @Override
    public boolean requiereAnticipo() {
        return false;
    }

    @Override
    public int calcularComision() {
        return 0;
    }

    @Override
    public TipoCanalReserva getCanal() {
        return TipoCanalReserva.DIRECTA;
    }

    public Recepcionista getRegistradaPor() {
        return registradaPor;
    }
}