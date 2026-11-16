package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ReservasController implements Refrescable {

    @FXML private ComboBox<String> cbCanal;
    @FXML private TextField txtCodigoExterno;
    @FXML private ComboBox<String> cbHuesped;
    @FXML private ComboBox<String> cbApartamento;
    @FXML private DatePicker dpEntrada;
    @FXML private DatePicker dpSalida;
    @FXML private TextField txtNombreOcupante;
    @FXML private DatePicker dpNacimientoOcupante;
    @FXML private ListView<String> listaOcupantes;
    @FXML private TextArea areaCotizacion;
    @FXML private ListView<String> listaReservas;
    @FXML private HBox accionesRecepcion;
    @FXML private Label lblAyudaHuesped;

    private boolean esHuesped;
    private ArrayList<Ocupante> ocupantes = new ArrayList<>();
    private ArrayList<Huesped> huespedesCombo = new ArrayList<>();
    private ArrayList<Apartamento> apartamentosCombo = new ArrayList<>();
    private ArrayList<Reserva> reservasVisibles = new ArrayList<>();

    @FXML
    public void initialize() {
        esHuesped = AppSGA.usuarioActual instanceof Huesped;
        if (esHuesped) {
            cbCanal.getItems().add("Portal");
            cbCanal.setValue("Portal");
            cbCanal.setDisable(true);
            cbHuesped.setDisable(true);
            AppSGA.ocultar(accionesRecepcion);
        } else {
            cbCanal.getItems().addAll("Directa", "Externa");
            cbCanal.setValue("Directa");
            AppSGA.ocultar(lblAyudaHuesped);
        }
        txtCodigoExterno.setDisable(true);
        cbCanal.setOnAction(e -> txtCodigoExterno.setDisable(!"Externa".equals(cbCanal.getValue())));
        refrescar();
    }

    @FXML
    private void agregarOcupante() {
        if (txtNombreOcupante.getText().isBlank() || dpNacimientoOcupante.getValue() == null) {
            AppSGA.mostrarError("Escriba el nombre y la fecha de nacimiento del ocupante.");
            return;
        }
        if (dpNacimientoOcupante.getValue().isAfter(LocalDate.now())) {
            AppSGA.mostrarError("La fecha de nacimiento no es válida.");
            return;
        }
        Ocupante ocupante = new Ocupante(txtNombreOcupante.getText().trim(), dpNacimientoOcupante.getValue());
        ocupantes.add(ocupante);
        listaOcupantes.getItems().add(ocupante.getNombre() + " - nació el " + ocupante.getFechaNacimiento());
        txtNombreOcupante.clear();
        dpNacimientoOcupante.setValue(null);
    }

    @FXML
    private void quitarOcupante() {
        int indice = listaOcupantes.getSelectionModel().getSelectedIndex();
        if (indice >= 0) {
            ocupantes.remove(indice);
            listaOcupantes.getItems().remove(indice);
        }
    }

    private Huesped titularSeleccionado() {
        if (esHuesped) {
            return (Huesped) AppSGA.usuarioActual;
        }
        int indice = cbHuesped.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return huespedesCombo.get(indice);
    }

    private Apartamento apartamentoSeleccionado() {
        int indice = cbApartamento.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return apartamentosCombo.get(indice);
    }

    private String validar() {
        Apartamento apto = apartamentoSeleccionado();
        Huesped titular = titularSeleccionado();
        LocalDate entrada = dpEntrada.getValue();
        LocalDate salida = dpSalida.getValue();

        if (titular == null) {
            return "Seleccione el titular de la reserva.";
        }
        if (apto == null) {
            return "Seleccione un apartamento.";
        }
        if (entrada == null || salida == null) {
            return "Seleccione la fecha de entrada y de salida.";
        }
        if (!salida.isAfter(entrada)) {
            return "La salida debe ser posterior a la entrada (mínimo una noche).";
        }
        if (entrada.isBefore(LocalDate.now())) {
            return "No se puede reservar con una fecha de entrada anterior a hoy.";
        }
        if (apto.getEstado() == EstadoApartamento.FUERA_SERVICIO) {
            return "El apartamento está fuera de servicio.";
        }
        if (ocupantes.size() + 1 > apto.getCapacidadMaxima()) {
            return "Supera la capacidad del apartamento (" + apto.getCapacidadMaxima() + " personas, contando al titular).";
        }
        if (apto.tieneBloqueoEn(entrada, salida)) {
            return "El apartamento tiene un bloqueo en esas fechas.";
        }
        return null;
    }

    private int contarPersonasQuePagan(Huesped titular, LocalDate entrada) {
        int contador = 0;
        if (titular.calcularEdad(entrada) >= 6) {
            contador++;
        }
        for (int i = 0; i < ocupantes.size(); i++) {
            if (ocupantes.get(i).calcularEdad(entrada) >= 6) {
                contador++;
            }
        }
        return contador;
    }

    private Cotizacion crearCotizacion(Apartamento apto, Huesped titular, LocalDate entrada, LocalDate salida) {
        Cotizacion cotizacion = new Cotizacion(apto, entrada, salida);
        int personas = contarPersonasQuePagan(titular, entrada);
        LocalDate noche = entrada;
        while (noche.isBefore(salida)) {
            TipoTemporada temporada = AppSGA.temporadaDe(noche);
            cotizacion.agregarNoche(new DetalleNoche(noche, temporada, apto.consultarTarifa(temporada), personas));
            noche = noche.plusDays(1);
        }
        return cotizacion;
    }

    @FXML
    private void cotizar() {
        String error = validar();
        if (error != null) {
            AppSGA.mostrarError(error);
            return;
        }
        Cotizacion cotizacion = crearCotizacion(apartamentoSeleccionado(), titularSeleccionado(),
                dpEntrada.getValue(), dpSalida.getValue());
        String texto = "Cotización " + cotizacion.getApartamento().getNombre() + "\n";
        ArrayList<DetalleNoche> detalle = cotizacion.getDetalle();
        for (int i = 0; i < detalle.size(); i++) {
            DetalleNoche d = detalle.get(i);
            texto += d.noche() + " | " + d.temporada() + " | $" + d.tarifa() + " x " + d.personasQuePagan()
                    + " personas = $" + d.calcularSubtotal() + "\n";
        }
        texto += "TOTAL: $" + cotizacion.getTotal() + " (" + cotizacion.getCantidadNoches() + " noches)";
        areaCotizacion.setText(texto);
    }

    @FXML
    private void crearReserva() {
        String error = validar();
        if (error != null) {
            AppSGA.mostrarError(error);
            return;
        }
        Apartamento apto = apartamentoSeleccionado();
        Huesped titular = titularSeleccionado();
        LocalDate entrada = dpEntrada.getValue();
        LocalDate salida = dpSalida.getValue();
        String canal = cbCanal.getValue();
        String codigoExterno = txtCodigoExterno.getText().trim();
        ArrayList<Reserva> reservas = AppSGA.alojamiento.getListaReservas();

        if ("Externa".equals(canal)) {
            if (codigoExterno.isEmpty()) {
                AppSGA.mostrarError("Escriba el código de la plataforma externa.");
                return;
            }
            for (int i = 0; i < reservas.size(); i++) {
                if (reservas.get(i) instanceof ReservaExterna) {
                    ReservaExterna externa = (ReservaExterna) reservas.get(i);
                    if (externa.getCodigoExterno().equals(codigoExterno)) {
                        AppSGA.mostrarError("Ese código externo ya creó una reserva.");
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (r.getApartamento() == apto && r.estaActiva() && r.chocaCon(entrada, salida)) {
                if ("Externa".equals(canal)) {
                    AppSGA.conflictos.add(new ConflictoCanal(codigoExterno, "Choca con la reserva " + r.getCodigo(), LocalDate.now()));
                    AppSGA.mostrarError("Conflicto de canal: la reserva externa choca con la reserva " + r.getCodigo()
                            + ". Se rechazó y quedó registrada como conflicto.");
                } else {
                    AppSGA.mostrarError("El apartamento ya está reservado en esas noches (reserva " + r.getCodigo() + ").");
                }
                return;
            }
        }

        Cotizacion cotizacion = crearCotizacion(apto, titular, entrada, salida);
        String codigo = String.format("R%03d", reservas.size() + 1);
        ArrayList<Ocupante> ocupantesReserva = new ArrayList<>(ocupantes);
        LocalDateTime ahora = LocalDateTime.now();
        Reserva reserva;

        if ("Portal".equals(canal)) {
            reserva = new ReservaPortal(codigo, apto, titular, ocupantesReserva, entrada, salida, ahora,
                    cotizacion.getTotal(), new PoliticaCancelacion());
        } else if ("Directa".equals(canal)) {
            Recepcionista recepcionista = null;
            if (AppSGA.usuarioActual instanceof Recepcionista) {
                recepcionista = (Recepcionista) AppSGA.usuarioActual;
            }
            reserva = new ReservaDirecta(codigo, apto, titular, ocupantesReserva, entrada, salida, ahora,
                    cotizacion.getTotal(), new PoliticaCancelacion(), recepcionista);
        } else {
            reserva = new ReservaExterna(codigo, apto, titular, ocupantesReserva, entrada, salida, ahora,
                    cotizacion.getTotal(), new PoliticaCancelacion(), codigoExterno);
        }

        AppSGA.alojamiento.registrarReserva(reserva);
        limpiar();
        refrescar();
        String mensaje = "Reserva " + codigo + " creada en estado PENDIENTE.\nValor de la estancia: $" + reserva.getValorEstancia();
        if (reserva.requiereAnticipo()) {
            mensaje += "\nAnticipo requerido (30 %): $" + reserva.calcularAnticipoRequerido() + " en las próximas 24 horas.";
        }
        if (reserva.calcularComision() > 0) {
            mensaje += "\nComisión para la plataforma (15 %): $" + reserva.calcularComision();
        }
        AppSGA.mostrarMensaje(mensaje);
    }

    private Reserva reservaSeleccionada() {
        int indice = listaReservas.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            AppSGA.mostrarError("Seleccione una reserva de la lista.");
            return null;
        }
        return reservasVisibles.get(indice);
    }

    @FXML
    private void confirmar() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        if (r.getEstado() != EstadoReserva.PENDIENTE) {
            AppSGA.mostrarError("Solo se pueden confirmar reservas pendientes.");
            return;
        }
        if (!r.puedeConfirmarse()) {
            AppSGA.mostrarError("Falta el anticipo: se requieren $" + r.calcularAnticipoRequerido()
                    + " y se han pagado $" + r.getCuenta().calcularTotalPagos() + ".");
            return;
        }
        r.cambiarEstado(EstadoReserva.CONFIRMADA);
        refrescar();
        AppSGA.mostrarMensaje("Reserva " + r.getCodigo() + " confirmada.");
    }

    @FXML
    private void checkIn() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        if (r.getEstado() != EstadoReserva.CONFIRMADA) {
            AppSGA.mostrarError("Solo se puede hacer check-in a reservas confirmadas.");
            return;
        }
        if (LocalDate.now().isBefore(r.getFechaEntrada())) {
            AppSGA.mostrarError("Todavía no ha llegado la fecha de entrada (" + r.getFechaEntrada() + ").");
            return;
        }
        if (r.getApartamento().getEstado() != EstadoApartamento.PREPARADO) {
            AppSGA.mostrarError("El apartamento no está preparado. Estado actual: " + r.getApartamento().getEstado());
            return;
        }
        r.cambiarEstado(EstadoReserva.EN_CURSO);
        r.getApartamento().cambiarEstado(EstadoApartamento.OCUPADO, "");
        refrescar();
        AppSGA.mostrarMensaje("Check-in realizado. Reserva en curso.");
    }

    @FXML
    private void checkOut() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        if (r.getEstado() != EstadoReserva.EN_CURSO) {
            AppSGA.mostrarError("Solo se puede hacer check-out a reservas en curso.");
            return;
        }
        r.cambiarEstado(EstadoReserva.FINALIZADA);
        r.getApartamento().cambiarEstado(EstadoApartamento.PENDIENTE_PREPARACION, "");
        refrescar();
        String mensaje = "Check-out realizado. El apartamento quedó pendiente de preparación.";
        if (r.calcularSaldo() > 0) {
            mensaje += "\nOjo: el folio tiene un saldo pendiente de $" + r.calcularSaldo();
        }
        AppSGA.mostrarMensaje(mensaje);
    }

    @FXML
    private void cancelar() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        if (r.getEstado() != EstadoReserva.PENDIENTE && r.getEstado() != EstadoReserva.CONFIRMADA) {
            AppSGA.mostrarError("Solo se pueden cancelar reservas pendientes o confirmadas.");
            return;
        }
        int devolucion = r.calcularDevolucion(LocalDateTime.now());
        Alert pregunta = new Alert(Alert.AlertType.CONFIRMATION);
        pregunta.setHeaderText(null);
        pregunta.setContentText("Pagado: $" + r.getCuenta().calcularTotalPagos() + "\nSe le devolverían: $" + devolucion
                + "\n¿Desea cancelar la reserva " + r.getCodigo() + "?");
        Optional<ButtonType> respuesta = pregunta.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            r.cambiarEstado(EstadoReserva.CANCELADA);
            refrescar();
            AppSGA.mostrarMensaje("Reserva cancelada. Devolución registrada: $" + devolucion);
        }
    }

    @FXML
    private void noShow() {
        Reserva r = reservaSeleccionada();
        if (r == null) {
            return;
        }
        if (r.getEstado() != EstadoReserva.CONFIRMADA) {
            AppSGA.mostrarError("Solo se puede declarar no-show en reservas confirmadas.");
            return;
        }
        if (LocalDateTime.now().isBefore(r.getFechaEntrada().atTime(22, 0))) {
            AppSGA.mostrarError("Solo se puede declarar no-show después de las 10:00 p. m. del día de entrada.");
            return;
        }
        r.cambiarEstado(EstadoReserva.NO_SHOW);
        refrescar();
        AppSGA.mostrarMensaje("Reserva marcada como no-show. Se retiene el 100 %.");
    }

    private void limpiar() {
        cbApartamento.getSelectionModel().clearSelection();
        dpEntrada.setValue(null);
        dpSalida.setValue(null);
        txtCodigoExterno.clear();
        ocupantes.clear();
        listaOcupantes.getItems().clear();
        areaCotizacion.clear();
    }

    @Override
    public void refrescar() {
        ArrayList<Reserva> reservas = AppSGA.alojamiento.getListaReservas();
        LocalDateTime ahora = LocalDateTime.now();
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).venceConfirmacion(ahora)) {
                reservas.get(i).cambiarEstado(EstadoReserva.CANCELADA);
            }
        }

        huespedesCombo.clear();
        cbHuesped.getItems().clear();
        ArrayList<Huesped> huespedes = AppSGA.alojamiento.getListaHuespedes();
        for (int i = 0; i < huespedes.size(); i++) {
            huespedesCombo.add(huespedes.get(i));
            cbHuesped.getItems().add(huespedes.get(i).getDocumento() + " - " + huespedes.get(i).getNombre());
        }
        if (esHuesped) {
            cbHuesped.getItems().clear();
            cbHuesped.getItems().add(((Huesped) AppSGA.usuarioActual).getNombre());
            cbHuesped.getSelectionModel().select(0);
        }

        apartamentosCombo.clear();
        cbApartamento.getItems().clear();
        ArrayList<Apartamento> apartamentos = AppSGA.alojamiento.getListaApartamentos();
        for (int i = 0; i < apartamentos.size(); i++) {
            Apartamento a = apartamentos.get(i);
            if (a.isActivo()) {
                apartamentosCombo.add(a);
                cbApartamento.getItems().add(a.getCodigo() + " - " + a.getNombre() + " (cap. " + a.getCapacidadMaxima() + ")");
            }
        }

        reservasVisibles.clear();
        listaReservas.getItems().clear();
        for (int i = 0; i < reservas.size(); i++) {
            Reserva r = reservas.get(i);
            if (esHuesped && r.getTitular() != AppSGA.usuarioActual) {
                continue;
            }
            reservasVisibles.add(r);
            listaReservas.getItems().add(r.getCodigo() + " | " + r.getApartamento().getCodigo() + " | "
                    + r.getTitular().getNombre() + " | " + r.getFechaEntrada() + " a " + r.getFechaSalida() + " | "
                    + r.getCanal() + " | " + r.getEstado() + " | Valor $" + r.getValorEstancia()
                    + " | Saldo $" + r.calcularSaldo());
        }
    }
}
