package co.edu.uniquindio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReservaExterna extends Reserva {

    private String codigoExterno;

    public ReservaExterna(String codigo, Apartamento apartamento,
                          Huesped titular, ArrayList<Ocupante> ocupantes,
                          LocalDate fechaEntrada, LocalDate fechaSalida,
                          LocalDateTime fechaCreacion, int valorEstancia,
                          PoliticaCancelacion politica, String codigoExterno) {

        super(codigo, apartamento, titular, ocupantes,
                fechaEntrada, fechaSalida, fechaCreacion,
                valorEstancia, politica);

        this.codigoExterno = codigoExterno;
    }

    @Override
    public boolean requiereAnticipo() {
        return false;
    }

    @Override
    public int calcularComision() {
        return getValorEstancia() * 15 / 100;
    }

    @Override
    public TipoCanalReserva getCanal() {
        return TipoCanalReserva.EXTERNA;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }
}
