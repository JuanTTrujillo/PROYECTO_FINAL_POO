package co.edu.uniquindio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Reserva {

    private String codigo;
    private Apartamento apartamento;
    private Huesped titular;
    private ArrayList<Ocupante> ocupantes;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalDateTime fechaCreacion;
    private EstadoReserva estado;
    private int valorEstancia;
    private PoliticaCancelacion politica;
    private CuentaPagar cuenta;

    public Reserva(String codigo, Apartamento apartamento, Huesped titular, ArrayList<Ocupante> ocupantes,
                   LocalDate fechaEntrada, LocalDate fechaSalida, LocalDateTime fechaCreacion,
                   int valorEstancia, PoliticaCancelacion politica) {
        this.codigo = codigo;
        this.apartamento = apartamento;
        this.titular = titular;
        this.ocupantes = ocupantes;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.fechaCreacion = fechaCreacion;
        this.valorEstancia = valorEstancia;
        this.politica = politica;
        this.estado = EstadoReserva.PENDIENTE;
        this.cuenta = new CuentaPagar();
        cuenta.registrarCargo(new Cargo(valorEstancia, fechaCreacion.toLocalDate(), "Alojamiento", TipoCargo.ALOJAMIENTO));
    }

    public abstract boolean requiereAnticipo();

    public abstract int calcularComision();

    public abstract TipoCanalReserva getCanal();

    public int calcularNoches() {
        return (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
    }

    public int contarOcupantes() {
        return ocupantes.size() + 1;
    }

    public int contarOcupantesQuePagan() {
        int contador = 0;
        if (titular.calcularEdad(fechaEntrada) >= 6) {
            contador++;
        }
        for (int i = 0; i < ocupantes.size(); i++) {
            if (ocupantes.get(i).calcularEdad(fechaEntrada) >= 6) {
                contador++;
            }
        }
        return contador;
    }

    public boolean chocaCon(LocalDate entrada, LocalDate salida) {
        return fechaEntrada.isBefore(salida) && entrada.isBefore(fechaSalida);
    }

    public boolean estaActiva() {
        return estado == EstadoReserva.PENDIENTE
                || estado == EstadoReserva.CONFIRMADA
                || estado == EstadoReserva.EN_CURSO;
    }

    public boolean cambiarEstado(EstadoReserva nuevo) {
        boolean permitido = false;

        if (estado == EstadoReserva.PENDIENTE) {
            if (nuevo == EstadoReserva.CONFIRMADA || nuevo == EstadoReserva.CANCELADA) {
                permitido = true;
            }
        } else if (estado == EstadoReserva.CONFIRMADA) {
            if (nuevo == EstadoReserva.EN_CURSO || nuevo == EstadoReserva.CANCELADA
                    || nuevo == EstadoReserva.NO_SHOW) {
                permitido = true;
            }
        } else if (estado == EstadoReserva.EN_CURSO) {
            if (nuevo == EstadoReserva.FINALIZADA) {
                permitido = true;
            }
        }

        if (permitido) {
            estado = nuevo;
        }
        return permitido;
    }

    public boolean venceConfirmacion(LocalDateTime ahora) {
        return estado == EstadoReserva.PENDIENTE && ahora.isAfter(fechaCreacion.plusHours(24));
    }

    public int calcularAnticipoRequerido() {
        if (requiereAnticipo()) {
            return valorEstancia * 30 / 100;
        }
        return 0;
    }

    public boolean puedeConfirmarse() {
        return estado == EstadoReserva.PENDIENTE
                && cuenta.calcularTotalPagos() >= calcularAnticipoRequerido();
    }

    public int calcularSaldo() {
        return cuenta.calcularSaldo();
    }

    public int calcularDevolucion(LocalDateTime ahora) {
        LocalDateTime horaEntrada = fechaEntrada.atTime(15, 0);
        long horasAntelacion = Duration.between(ahora, horaEntrada).toHours();
        boolean noShow = estado == EstadoReserva.NO_SHOW;
        return politica.calcularDevolucion(valorEstancia, cuenta.calcularTotalPagos(), horasAntelacion, noShow);
    }

    public String getCodigo() {
        return codigo;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public Huesped getTitular() {
        return titular;
    }

    public ArrayList<Ocupante> getOcupantes() {
        return ocupantes;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public int getValorEstancia() {
        return valorEstancia;
    }

    public PoliticaCancelacion getPolitica() {
        return politica;
    }

    public CuentaPagar getCuenta() {
        return cuenta;
    }
}