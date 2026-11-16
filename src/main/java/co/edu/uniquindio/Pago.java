package co.edu.uniquindio;

import java.time.LocalDate;

public class Pago extends Movimiento {

    private MedioPago medio;

    public Pago(int monto, LocalDate fecha, String descripcion, MedioPago medio) {
        super(monto, fecha, descripcion);
        this.medio = medio;
    }

    public MedioPago getMedio() {
        return medio;
    }

}