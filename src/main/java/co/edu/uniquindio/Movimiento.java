package co.edu.uniquindio;

import java.time.LocalDate;

public abstract class Movimiento {

    private int monto;
    private LocalDate fecha;
    private String descripcion;

    public Movimiento(int monto, LocalDate fecha, String descripcion) {
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}