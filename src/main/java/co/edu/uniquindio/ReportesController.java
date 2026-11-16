package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ReportesController implements Refrescable {

    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    @FXML private TextArea areaResultado;

    @FXML
    public void initialize() {
        dpInicio.setValue(LocalDate.now().withDayOfMonth(1));
        dpFin.setValue(LocalDate.now().withDayOfMonth(1).plusMonths(1));
    }

    @Override
    public void refrescar() {
        areaResultado.clear();
    }

    private Reporte crearReporte() {
        return new Reporte(AppSGA.alojamiento.getListaReservas(), AppSGA.alojamiento.getListaApartamentos());
    }

    private boolean fechasValidas() {
        if (dpInicio.getValue() == null || dpFin.getValue() == null || !dpFin.getValue().isAfter(dpInicio.getValue())) {
            AppSGA.mostrarError("Seleccione un periodo válido.");
            return false;
        }
        return true;
    }

    @FXML
    private void ocupacion() {
        if (!fechasValidas()) {
            return;
        }
        Reporte reporte = crearReporte();
        long totalNoches = ChronoUnit.DAYS.between(dpInicio.getValue(), dpFin.getValue());
        String texto = "OCUPACIÓN DEL " + dpInicio.getValue() + " AL " + dpFin.getValue() + " (" + totalNoches + " noches)\n\n";
        ArrayList<Apartamento> apartamentos = AppSGA.alojamiento.getListaApartamentos();
        for (int i = 0; i < apartamentos.size(); i++) {
            int noches = reporte.calcularNochesOcupadas(apartamentos.get(i), dpInicio.getValue(), dpFin.getValue());
            long porcentaje = noches * 100 / totalNoches;
            texto += apartamentos.get(i).getCodigo() + " - " + apartamentos.get(i).getNombre() + ": " + noches
                    + " noches ocupadas (" + porcentaje + " %)\n";
        }
        areaResultado.setText(texto);
    }

    @FXML
    private void ingresos() {
        if (!fechasValidas()) {
            return;
        }
        int total = crearReporte().calcularIngresosPorPeriodo(dpInicio.getValue(), dpFin.getValue());
        areaResultado.setText("INGRESOS DEL " + dpInicio.getValue() + " AL " + dpFin.getValue() + "\n\nTotal recibido en pagos: $" + total);
    }

    @FXML
    private void reservasPorCanal() {
        Reporte reporte = crearReporte();
        String texto = "RESERVAS POR CANAL\n\n";
        TipoCanalReserva[] canales = TipoCanalReserva.values();
        for (int i = 0; i < canales.length; i++) {
            texto += canales[i] + ": " + reporte.contarReservasPorCanal(canales[i]) + " en total"
                    + " | canceladas: " + reporte.contarReservasPorCanalYEstado(canales[i], EstadoReserva.CANCELADA)
                    + " | no-show: " + reporte.contarReservasPorCanalYEstado(canales[i], EstadoReserva.NO_SHOW) + "\n";
        }
        areaResultado.setText(texto);
    }

    @FXML
    private void saldosPendientes() {
        ArrayList<Reserva> pendientes = crearReporte().listarSaldosPendientes();
        String texto = "RESERVAS CON SALDO PENDIENTE\n\n";
        int total = 0;
        for (int i = 0; i < pendientes.size(); i++) {
            Reserva r = pendientes.get(i);
            texto += r.getCodigo() + " | " + r.getTitular().getNombre() + " | " + r.getEstado() + " | Saldo $" + r.calcularSaldo() + "\n";
            total += r.calcularSaldo();
        }
        texto += "\nTotal por cobrar: $" + total;
        areaResultado.setText(texto);
    }

    @FXML
    private void novedades() {
        ArrayList<Apartamento> lista = crearReporte().listarApartamentosConMasDe(0);
        String texto = "APARTAMENTOS CON NOVEDADES\n\n";
        for (int i = 0; i < lista.size(); i++) {
            texto += lista.get(i).getCodigo() + " - " + lista.get(i).getNombre() + ": " + lista.get(i).contarNovedades() + " novedades\n";
        }
        if (lista.isEmpty()) {
            texto += "No hay novedades registradas.";
        }
        areaResultado.setText(texto);
    }

    @FXML
    private void conflictos() {
        String texto = "CONFLICTOS DE CANAL\n\n";
        for (int i = 0; i < AppSGA.conflictos.size(); i++) {
            ConflictoCanal c = AppSGA.conflictos.get(i);
            texto += c.fechaRegistro() + " | código " + c.codigoExterno() + " | " + c.motivo() + "\n";
        }
        if (AppSGA.conflictos.isEmpty()) {
            texto += "No hay conflictos registrados.";
        }
        areaResultado.setText(texto);
    }
}
