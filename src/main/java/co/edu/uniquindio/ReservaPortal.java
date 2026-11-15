package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservaPortal extends Reserva {

    public ReservaPortal(String codigo, Apartamento apartamento, Huesped titular, ArrayList<Ocupante> ocupantes,
                         LocalDate fechaEntrada, LocalDate fechaSalida, LocalDateTime fechaCreacion,
                         int valorEstancia, PoliticaCancelacion politica) {
        super(codigo, apartamento, titular, ocupantes, fechaEntrada, fechaSalida, fechaCreacion, valorEstancia, politica);
    }

    @Override
    public boolean requiereAnticipo() {
        return true;
    }

    @Override
    public int calcularComision() {
        return 0;
    }

    @Override
    public TipoCanalReserva getCanal() {
        return TipoCanalReserva.PORTAL;
    }
}