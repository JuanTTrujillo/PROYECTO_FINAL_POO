package co.edu.uniquindio;
import java.util.ArrayList;

public class Alojamiento {

    private String nombre;
    private ArrayList<Apartamento> listaApartamentos;
    private ArrayList<Reserva> listaReservas;
    private ArrayList<Huesped> listaHuespedes;
    private ArrayList<Empleado> listaEmpleados;

    public Alojamiento(String nombre) {
        this.nombre = nombre;
        this.listaApartamentos = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaHuespedes = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
    }

    public void registrarApartamento(Apartamento apartamento) {
        if (apartamento != null) {
            listaApartamentos.add(apartamento);
        }
    }

    public void registrarReserva(Reserva reserva) {
        if (reserva != null) {
            listaReservas.add(reserva);
        }
    }

    public void registrarHuesped(Huesped huesped) {
        if (huesped != null) {
            listaHuespedes.add(huesped);
        }
    }

    public void registrarEmpleado(Empleado empleado) {
        if (empleado != null) {
            listaEmpleados.add(empleado);
        }
    }

    public Apartamento buscarApartamento(String codigo) {
        for (int i = 0; i < listaApartamentos.size(); i++) {
            if (listaApartamentos.get(i).getCodigo().equals(codigo)) {
                return listaApartamentos.get(i);
            }
        }
        return null;
    }

    public Reserva buscarReserva(String codigo) {
        for (int i = 0; i < listaReservas.size(); i++) {
            if (listaReservas.get(i).getCodigo().equals(codigo)) {
                return listaReservas.get(i);
            }
        }
        return null;
    }

    public Huesped buscarHuesped(String documento) {
        for (int i = 0; i < listaHuespedes.size(); i++) {
            if (listaHuespedes.get(i).getDocumento().equals(documento)) {
                return listaHuespedes.get(i);
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Apartamento> getListaApartamentos() {
        return listaApartamentos;
    }

    public ArrayList<Reserva> getListaReservas() {
        return listaReservas;
    }

    public ArrayList<Huesped> getListaHuespedes() {
        return listaHuespedes;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }
}
