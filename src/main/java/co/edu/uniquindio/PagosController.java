package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class PagosController implements Refrescable {

    @FXML private ListView<String> listaReservas;
    @FXML private TextArea areaFolio;
    @FXML private TextField txtMontoPago;
    @FXML private ComboBox<MedioPago> cbMedio;
    @FXML private VBox seccionCargo;
    @FXML private TextField txtDescripcionCargo;
    @FXML private TextField txtMontoCargo;

    private boolean esHuesped;
    private ArrayList<Reserva> reservasVisibles = new ArrayList<>();

    @FXML
    public void initialize() {
        esHuesped = AppSGA.usuarioActual instanceof Huesped;
        cbMedio.getItems().addAll(MedioPago.values());
        cbMedio.setValue(MedioPago.EFECTIVO);
        if (esHuesped) {
            AppSGA.ocultar(seccionCargo);
        }
        refrescar();
    }

    private Reserva reservaSeleccionada() {
        int indice = listaReservas.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return reservasVisibles.get(indice);
    }

    @FXML
    private void mostrarFolio() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        CuentaPagar cuenta = r.getCuenta();
        String texto = "Reserva " + r.getCodigo() + " - " + r.getEstado() + "\n\nCARGOS\n";
        ArrayList<Cargo> cargos = cuenta.getListaCargos();
        for (int i = 0; i < cargos.size(); i++) {
            texto += cargos.get(i).getFecha() + " | " + cargos.get(i).getTipo() + " | " + cargos.get(i).getDescripcion()
                    + " | $" + cargos.get(i).getMonto() + "\n";
        }
        texto += "\nPAGOS\n";
        ArrayList<Pago> pagos = cuenta.getListaPagos();
        for (int i = 0; i < pagos.size(); i++) {
            texto += pagos.get(i).getFecha() + " | " + pagos.get(i).getMedio() + " | " + pagos.get(i).getDescripcion()
                    + " | $" + pagos.get(i).getMonto() + "\n";
        }
        texto += "\nTotal cargos: $" + cuenta.calcularTotalCargos();
        texto += "\nTotal pagos: $" + cuenta.calcularTotalPagos();
        texto += "\nSALDO: $" + cuenta.calcularSaldo();
        if (r.getEstado() == EstadoReserva.PENDIENTE && r.requiereAnticipo()) {
            texto += "\nAnticipo requerido para confirmar: $" + r.calcularAnticipoRequerido();
        }
        areaFolio.setText(texto);
    }

    @FXML
    private void registrarPago() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            AppSGA.mostrarError("Seleccione una reserva.");
            return;
        }
        if (r.getEstado() == EstadoReserva.CANCELADA || r.getEstado() == EstadoReserva.NO_SHOW) {
            AppSGA.mostrarError("No se pueden registrar pagos en una reserva " + r.getEstado() + ".");
            return;
        }
        int monto = AppSGA.leerNumero(txtMontoPago.getText());
        if (monto <= 0) {
            AppSGA.mostrarError("El monto debe ser un número mayor que 0 (sin puntos ni decimales).");
            return;
        }
        if (monto > r.calcularSaldo()) {
            AppSGA.mostrarError("El pago supera el saldo pendiente ($" + r.calcularSaldo() + ").");
            return;
        }
        r.getCuenta().registrarPago(new Pago(monto, LocalDate.now(), "Abono", cbMedio.getValue()));
        txtMontoPago.clear();
        refrescar();
        mostrarFolio();
        String mensaje = "Pago registrado.";
        if (r.getEstado() == EstadoReserva.PENDIENTE && r.puedeConfirmarse()) {
            mensaje += " Ya cubre el anticipo, la recepción puede confirmar la reserva.";
        }
        AppSGA.mostrarMensaje(mensaje);
    }

    @FXML
    private void registrarCargo() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            AppSGA.mostrarError("Seleccione una reserva.");
            return;
        }
        int monto = AppSGA.leerNumero(txtMontoCargo.getText());
        if (txtDescripcionCargo.getText().isBlank() || monto <= 0) {
            AppSGA.mostrarError("Escriba la descripción y un monto mayor que 0.");
            return;
        }
        r.getCuenta().registrarCargo(new Cargo(monto, LocalDate.now(), txtDescripcionCargo.getText().trim(), TipoCargo.SERVICIO_ADICIONAL));
        txtDescripcionCargo.clear();
        txtMontoCargo.clear();
        refrescar();
        mostrarFolio();
    }

    @Override
    public void refrescar() {
        int seleccion = listaReservas.getSelectionModel().getSelectedIndex();
        reservasVisibles.clear();
        listaReservas.getItems().clear();
        ArrayList<Reserva> reservas = AppSGA.alojamiento.getListaReservas();
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (esHuesped && r.getTitular() != AppSGA.usuarioActual) {
                continue;
            }
            reservasVisibles.add(r);
            listaReservas.getItems().add(r.getCodigo() + " | " + r.getTitular().getNombre() + " | " + r.getEstado()
                    + " | Saldo $" + r.calcularSaldo());
        }
        if (seleccion >= 0 && seleccion < reservasVisibles.size()) {
            listaReservas.getSelectionModel().select(seleccion);
        }
    }
}
