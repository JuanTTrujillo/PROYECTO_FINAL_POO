package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class HuespedesController implements Refrescable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private DatePicker dpNacimiento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private VBox seccionLista;
    @FXML private ListView<String> lista;

    @FXML
    public void initialize() {
        if (AppSGA.usuarioActual == null) {
            AppSGA.ocultar(seccionLista);
        }
        refrescar();
    }

    @FXML
    private void registrar() {
        if (txtNombre.getText().isBlank() || txtDocumento.getText().isBlank() || txtTelefono.getText().isBlank()
                || txtCorreo.getText().isBlank() || txtUsuario.getText().isBlank()
                || txtContrasena.getText().isBlank() || dpNacimiento.getValue() == null) {
            AppSGA.mostrarError("Llene todos los campos.");
            return;
        }
        if (!dpNacimiento.getValue().isBefore(LocalDate.now())) {
            AppSGA.mostrarError("La fecha de nacimiento no es válida.");
            return;
        }
        if (AppSGA.alojamiento.buscarHuesped(txtDocumento.getText().trim()) != null) {
            AppSGA.mostrarError("Ya existe un huésped con ese documento.");
            return;
        }
        if (AppSGA.usuarioExiste(txtUsuario.getText().trim())) {
            AppSGA.mostrarError("Ese usuario ya está en uso.");
            return;
        }

        Huesped huesped = new Huesped(txtNombre.getText().trim(), dpNacimiento.getValue(),
                txtDocumento.getText().trim(), txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                txtUsuario.getText().trim(), txtContrasena.getText());
        AppSGA.alojamiento.registrarHuesped(huesped);

        limpiar();
        refrescar();
        AppSGA.mostrarMensaje("Huésped registrado. Ya puede iniciar sesión con su usuario.");
    }

    private void limpiar() {
        txtNombre.clear();
        txtDocumento.clear();
        dpNacimiento.setValue(null);
        txtTelefono.clear();
        txtCorreo.clear();
        txtUsuario.clear();
        txtContrasena.clear();
    }

    @Override
    public void refrescar() {
        lista.getItems().clear();
        ArrayList<Huesped> huespedes = AppSGA.alojamiento.getListaHuespedes();
        for (int i = 0; i < huespedes.size(); i++) {
            Huesped h = huespedes.get(i);
            lista.getItems().add(h.getDocumento() + " | " + h.getNombre() + " | " + h.getTelefono() + " | " + h.getCorreo());
        }
    }
}
