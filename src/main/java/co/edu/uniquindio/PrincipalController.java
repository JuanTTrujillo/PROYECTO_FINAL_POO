package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class PrincipalController {

    @FXML private Label lblUsuario;
    @FXML private TabPane pestanas;

    @FXML
    public void initialize() {
        Persona persona = (Persona) AppSGA.usuarioActual;
        Rol rol = AppSGA.usuarioActual.getRol();
        lblUsuario.setText("Sesión: " + persona.getNombre() + " (" + rol + ")");

        if (rol == Rol.ADMINISTRADOR) {
            agregarPestana("Apartamentos", "Apartamentos.fxml");
            agregarPestana("Reservas", "Reservas.fxml");
            agregarPestana("Pagos", "Pagos.fxml");
            agregarPestana("Huéspedes", "Huespedes.fxml");
            agregarPestana("Empleados", "Empleados.fxml");
            agregarPestana("Reportes", "Reportes.fxml");
        } else if (rol == Rol.RECEPCIONISTA) {
            agregarPestana("Reservas", "Reservas.fxml");
            agregarPestana("Pagos", "Pagos.fxml");
            agregarPestana("Huéspedes", "Huespedes.fxml");
            agregarPestana("Apartamentos", "Apartamentos.fxml");
        } else {
            agregarPestana("Mis reservas", "Reservas.fxml");
            agregarPestana("Mis pagos", "Pagos.fxml");
        }
    }

    private void agregarPestana(String titulo, String archivo) {
        try {
            FXMLLoader loader = new FXMLLoader(AppSGA.class.getResource(archivo));
            Parent contenido = loader.load();
            Refrescable controlador = loader.getController();

            ScrollPane scroll = new ScrollPane(contenido);
            scroll.setFitToWidth(true);
            Tab tab = new Tab(titulo, scroll);
            tab.setOnSelectionChanged(e -> {
                if (tab.isSelected()) {
                    controlador.refrescar();
                }
            });
            pestanas.getTabs().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
            AppSGA.mostrarError("No se pudo abrir la pestaña " + titulo);
        }
    }

    @FXML
    private void cerrarSesion() {
        AppSGA.usuarioActual = null;
        AppSGA.cambiarVista("Login.fxml", 420, 400);
    }
}
