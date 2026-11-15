package co.edu.uniquindio;

import java.time.LocalDate;

public class Cargo extends Movimiento {

    private TipoCargo tipo;

    public Cargo(int monto, LocalDate fecha, String descripcion, TipoCargo tipo) {
        super(monto, fecha, descripcion);
        this.tipo = tipo;
    }

    public TipoCargo getTipo() {
        return tipo;
    }

    public void setTipo(TipoCargo tipo) {
        this.tipo = tipo;
    }
}