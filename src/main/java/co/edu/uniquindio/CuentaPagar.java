package co.edu.uniquindio;
import java.util.ArrayList;

public class CuentaPagar {

    private ArrayList<Cargo> listaCargos;
    private ArrayList<Pago> listaPagos;

    public CuentaPagar() {
        listaCargos = new ArrayList<>();
        listaPagos = new ArrayList<>();
    }

    public void registrarCargo(Cargo cargo) {
        if (cargo != null) {
            listaCargos.add(cargo);
        }
    }

    public void registrarPago(Pago pago) {
        if (pago != null) {
            listaPagos.add(pago);
        }
    }

    public int calcularTotalCargos() {
        int total = 0;

        for (int i = 0; i < listaCargos.size(); i++) {
            total += listaCargos.get(i).getMonto();
        }

        return total;
    }

    public int calcularTotalPagos() {
        int total = 0;

        for (int i = 0; i < listaPagos.size(); i++) {
            total += listaPagos.get(i).getMonto();
        }

        return total;
    }

    public int calcularSaldo() {
        return calcularTotalCargos() - calcularTotalPagos();
    }

    public ArrayList<Cargo> getListaCargos() {
        return listaCargos;
    }

    public ArrayList<Pago> getListaPagos() {
        return listaPagos;
    }

    public ArrayList<Pago> getPagos() {
        return listaPagos;
    }

    public ArrayList<Cargo> getCargos() {
        return listaCargos;
    }
}