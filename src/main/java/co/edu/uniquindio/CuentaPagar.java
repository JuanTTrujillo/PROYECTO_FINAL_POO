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

}
