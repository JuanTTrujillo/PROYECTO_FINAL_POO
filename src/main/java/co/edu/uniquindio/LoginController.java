package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    @FXML
    private void ingresar() {
        Autenticable usuario = buscarUsuario(txtUsuario.getText(), txtContrasena.getText());
        if (usuario == null) {
            AppSGA.mostrarError("Usuario o contraseña incorrectos, o el usuario está inactivo.");
            return;
        }
        AppSGA.usuarioActual = usuario;
        AppSGA.cambiarVista("Principal.fxml", 1100, 720);
    }

    @FXML
    private void registrarse() {
        AppSGA.abrirVentana("Huespedes.fxml", "Registro de huésped", 460, 420);
    }

    private Autenticable buscarUsuario(String usuario, String contrasena) {
        ArrayList<Empleado> empleados = AppSGA.alojamiento.getListaEmpleados();
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).autenticar(usuario, contrasena)) {
                return empleados.get(i);
            }
        }
        ArrayList<Huesped> huespedes = AppSGA.alojamiento.getListaHuespedes();
        for (int i = 0; i < huespedes.size(); i++) {
            if (huespedes.get(i).autenticar(usuario, contrasena)) {
                return huespedes.get(i);
            }
        }
        return null;
    }
}
