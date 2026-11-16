package co.edu.uniquindio;
import java.util.LinkedList;
public class CuentaPagar {

    private LinkedList<Cargo> listaCargos;
    private LinkedList<Pago> listaPagos;

    public CuentaPagar() {
        listaCargos = new LinkedList<>();
        listaPagos = new LinkedList<>();
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

    public LinkedList<Cargo> getListaCargos() {
        return listaCargos;
    }

    public LinkedList<Pago> getListaPagos() {
        return listaPagos;
    }
}

