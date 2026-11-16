package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmpleadosController implements Refrescable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private DatePicker dpNacimiento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private ListView<String> lista;

    @FXML
    public void initialize() {
        refrescar();
    }

    @FXML
    private void contratar() {
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
        if (AppSGA.usuarioExiste(txtUsuario.getText().trim())) {
            AppSGA.mostrarError("Ese usuario ya está en uso.");
            return;
        }
        Recepcionista recepcionista = new Recepcionista(txtNombre.getText().trim(), dpNacimiento.getValue(),
                txtDocumento.getText().trim(), txtTelefono.getText().trim(), txtCorreo.getText().trim(),
                txtUsuario.getText().trim(), txtContrasena.getText());
        AppSGA.alojamiento.registrarEmpleado(recepcionista);
        limpiar();
        refrescar();
        AppSGA.mostrarMensaje("Recepcionista contratado.");
    }

    private Empleado seleccionado() {
        int indice = lista.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return AppSGA.alojamiento.getListaEmpleados().get(indice);
    }

    @FXML
    private void cargarSeleccionado() {
        Empleado empleado = seleccionado();
        if (empleado != null) {
            txtNombre.setText(empleado.getNombre());
            txtTelefono.setText(empleado.getTelefono());
            txtCorreo.setText(empleado.getCorreo());
        }
    }

    @FXML
    private void actualizar() {
        Empleado empleado = seleccionado();
        if (empleado == null) {
            AppSGA.mostrarError("Seleccione un empleado de la lista.");
            return;
        }
        if (txtNombre.getText().isBlank() || txtTelefono.getText().isBlank() || txtCorreo.getText().isBlank()) {
            AppSGA.mostrarError("Nombre, teléfono y correo no pueden estar vacíos.");
            return;
        }
        empleado.actualizarDatos(txtNombre.getText().trim(), txtTelefono.getText().trim(), txtCorreo.getText().trim());
        limpiar();
        refrescar();
        AppSGA.mostrarMensaje("Datos actualizados.");
    }

    @FXML
    private void desvincular() {
        Empleado empleado = seleccionado();
        if (empleado == null) {
            AppSGA.mostrarError("Seleccione un empleado de la lista.");
            return;
        }
        if (empleado == AppSGA.usuarioActual) {
            AppSGA.mostrarError("No se puede desvincular a sí mismo.");
            return;
        }
        empleado.desactivar();
        refrescar();
        AppSGA.mostrarMensaje(empleado.getNombre() + " fue desvinculado.");
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
        ArrayList<Empleado> empleados = AppSGA.alojamiento.getListaEmpleados();
        for (int i = 0; i < empleados.size(); i++) {
            Empleado emp = empleados.get(i);
            String estado = "activo";
            if (!emp.isActivo()) {
                estado = "desvinculado";
            }
            lista.getItems().add(emp.getRol() + " | " + emp.getNombre() + " | " + emp.getUsuario() + " | "
                    + emp.getTelefono() + " | " + estado);
        }
    }
}
