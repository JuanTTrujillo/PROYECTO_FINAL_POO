package co.edu.uniquindio;

import java.util.ArrayList;

public class Reporte {

    private ArrayList<Reserva> reservas;
    private ArrayList<Apartamento> apartamentos;

    public Reporte(ArrayList<Reserva> reservas, ArrayList<Apartamento> apartamentos) {
        this.reservas = reservas;
        this.apartamentos = apartamentos;
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

    public ArrayList<Apartamento> listarApartamentosConMasDe(int n) {
        ArrayList<Apartamento> lista = new ArrayList<>();
        for (int i = 0; i < apartamentos.size(); i++) {
            if (apartamentos.get(i).contarNovedades() > n) {
                lista.add(apartamentos.get(i));
            }
        }
        return lista;
    }

// Falta: sacar cuántas noches estuvo ocupado un apartamento entre dos fechas
// Falta: sumar lo que entró de plata en un periodo
// Falta: sacar las reservas que todavía deben plata