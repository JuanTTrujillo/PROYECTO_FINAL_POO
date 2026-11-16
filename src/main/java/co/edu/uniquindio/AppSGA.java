package co.edu.uniquindio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppSGA extends Application {

    public static Alojamiento alojamiento = new Alojamiento("Aparta-Suites UQ");
    public static ArrayList<PeriodoTemporada> temporadas = new ArrayList<>();
    public static ArrayList<ConflictoCanal> conflictos = new ArrayList<>();
    public static Autenticable usuarioActual;

    private static Stage ventana;

    @Override
    public void start(Stage stage) {
        ventana = stage;
        ventana.setTitle("SGA - Aparta-Suites UQ");
        if (alojamiento.getListaEmpleados().isEmpty()) {
            cambiarVista("CrearAdministrador.fxml", 540, 480);
        } else {
            cambiarVista("Login.fxml", 420, 400);
        }
        ventana.show();
    }

    public static void cambiarVista(String archivo, double ancho, double alto) {
        try {
            Parent raiz = FXMLLoader.load(AppSGA.class.getResource(archivo));
            ventana.setScene(new Scene(raiz, ancho, alto));
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo abrir la ventana " + archivo);
        }
    }

    public static void abrirVentana(String archivo, String titulo, double ancho, double alto) {
        try {
            Parent raiz = FXMLLoader.load(AppSGA.class.getResource(archivo));
            Stage nueva = new Stage();
            nueva.setTitle(titulo);
            nueva.setScene(new Scene(raiz, ancho, alto));
            nueva.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo abrir la ventana " + archivo);
        }
    }

    public static void mostrarMensaje(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public static void mostrarError(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public static int leerNumero(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean usuarioExiste(String usuario) {
        ArrayList<Empleado> empleados = alojamiento.getListaEmpleados();
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getUsuario().equals(usuario)) {
                return true;
            }
        }
        ArrayList<Huesped> huespedes = alojamiento.getListaHuespedes();
        for (int i = 0; i < huespedes.size(); i++) {
            if (huespedes.get(i).getUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    public static TipoTemporada temporadaDe(LocalDate fecha) {
        for (int i = 0; i < temporadas.size(); i++) {
            PeriodoTemporada p = temporadas.get(i);
            if (!fecha.isBefore(p.fechaInicio()) && !fecha.isAfter(p.fechaFin())) {
                return p.tipoTemporada();
            }
        }
        return TipoTemporada.BAJA;
    }

    public static void ocultar(javafx.scene.Node nodo) {
        nodo.setVisible(false);
        nodo.setManaged(false);
    }
}
