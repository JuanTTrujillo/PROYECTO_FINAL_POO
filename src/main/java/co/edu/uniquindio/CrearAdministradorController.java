package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class CrearAdministradorController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private DatePicker dpNacimiento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML
    private void crearAdministrador() {
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
        Administrador admin = new Administrador(txtNombre.getText().trim(), dpNacimiento.getValue(),
                txtDocumento.getText().trim(), txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                txtUsuario.getText().trim(), txtContrasena.getText());
        AppSGA.alojamiento.registrarEmpleado(admin);
        AppSGA.mostrarMensaje("Administrador creado. Ahora inicie sesión.");
        AppSGA.cambiarVista("Login.fxml", 420, 400);
    }
}
