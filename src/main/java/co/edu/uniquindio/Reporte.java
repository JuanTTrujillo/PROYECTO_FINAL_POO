package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Reporte {

    private ArrayList<Reserva> reservas;
    private ArrayList<Apartamento> apartamentos;

    public Reporte(ArrayList<Reserva> reservas, ArrayList<Apartamento> apartamentos) {
        this.reservas = reservas;
        this.apartamentos = apartamentos;
    }

    public int calcularNochesOcupadas(Apartamento apto, LocalDate inicio, LocalDate fin) {
        int noches = 0;
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getApartamento() == apto
                    && r.getEstado() != EstadoReserva.CANCELADA
                    && r.getEstado() != EstadoReserva.NO_SHOW) {

                LocalDate desde = r.getFechaEntrada();
                if (desde.isBefore(inicio)) {
                    desde = inicio;
                }
                LocalDate hasta = r.getFechaSalida();
                if (hasta.isAfter(fin)) {
                    hasta = fin;
                }
                if (desde.isBefore(hasta)) {
                    noches += (int) ChronoUnit.DAYS.between(desde, hasta);
                }
            }
        }
        return noches;
    }

    public int calcularIngresosPorPeriodo(LocalDate inicio, LocalDate fin) {
        int total = 0;
        for (int i = 0; i < reservas.size(); i++) {
            ArrayList<Pago> pagos = reservas.get(i).getCuenta().getPagos();
            for (int j = 0; j < pagos.size(); j++) {
                LocalDate fecha = pagos.get(j).getFecha();
                if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                    total += pagos.get(j).getMonto();
                }
            }
        }
        return total;
    }

    public int contarReservasPorCanal(TipoCanalReserva canal) {
        int contador = 0;
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getCanal() == canal) {
                contador++;
            }
        }
        return contador;
    }

    public int contarReservasPorCanalYEstado(TipoCanalReserva canal, EstadoReserva estado) {
        int contador = 0;
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getCanal() == canal && r.getEstado() == estado) {
                contador++;
            }
        }
        return contador;
    }

    public ArrayList<Reserva> listarSaldosPendientes() {
        ArrayList<Reserva> pendientes = new ArrayList<>();
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).calcularSaldo() > 0) {
                pendientes.add(reservas.get(i));
            }
        }
        return pendientes;
    }

    public ArrayList<Apartamento> listarApartamentosConMasDe(int n) {
        ArrayList<Apartamento> lista = new ArrayList<>();
        for (int i = 0; i < apartamentos.size(); i++) {
            if (apartamentos.get(i).contarNovedades() > n) {
                lista.add(apartamentos.get(i));
            }
        }
        return lista;
    }
}