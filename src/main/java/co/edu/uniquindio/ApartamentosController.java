package co.edu.uniquindio;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;

public class ApartamentosController implements Refrescable {

    @FXML private ListView<String> lista;
    @FXML private ComboBox<EstadoApartamento> cbEstado;
    @FXML private TextField txtMotivo;
    @FXML private ComboBox<TipoNovedad> cbNovedad;
    @FXML private TextField txtNovedad;
    @FXML private VBox seccionAdmin;
    @FXML private DatePicker dpBloqueoInicio;
    @FXML private DatePicker dpBloqueoFin;
    @FXML private TextField txtMotivoBloqueo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDormitorios;
    @FXML private TextField txtCapacidad;
    @FXML private TextField txtCaracteristicas;
    @FXML private TextField txtTarifaBaja;
    @FXML private TextField txtTarifaMedia;
    @FXML private TextField txtTarifaAlta;
    @FXML private ComboBox<TipoTemporada> cbTemporada;
    @FXML private DatePicker dpTemporadaInicio;
    @FXML private DatePicker dpTemporadaFin;
    @FXML private ListView<String> listaTemporadas;

    private boolean esAdmin;

    @FXML
    public void initialize() {
        esAdmin = AppSGA.usuarioActual.getRol() == Rol.ADMINISTRADOR;
        cbEstado.getItems().addAll(EstadoApartamento.values());
        cbNovedad.getItems().addAll(TipoNovedad.values());
        cbTemporada.getItems().addAll(TipoTemporada.values());
        if (!esAdmin) {
            AppSGA.ocultar(seccionAdmin);
        }
        refrescar();
    }

    private ArrayList<String> leerCaracteristicas() {
        ArrayList<String> caracteristicas = new ArrayList<>();
        String[] partes = txtCaracteristicas.getText().split(",");
        for (int i = 0; i < partes.length; i++) {
            if (!partes[i].isBlank()) {
                caracteristicas.add(partes[i].trim());
            }
        }
        return caracteristicas;
    }

    @FXML
    private void crear() {
        int dormitorios = AppSGA.leerNumero(txtDormitorios.getText());
        int capacidad = AppSGA.leerNumero(txtCapacidad.getText());
        int baja = AppSGA.leerNumero(txtTarifaBaja.getText());
        int media = AppSGA.leerNumero(txtTarifaMedia.getText());
        int alta = AppSGA.leerNumero(txtTarifaAlta.getText());

        if (txtCodigo.getText().isBlank() || txtNombre.getText().isBlank()) {
            AppSGA.mostrarError("El código y el nombre son obligatorios.");
            return;
        }
        if (dormitorios <= 0 || capacidad <= 0 || baja <= 0 || media <= 0 || alta <= 0) {
            AppSGA.mostrarError("Dormitorios, capacidad y tarifas deben ser números mayores que 0.");
            return;
        }
        if (AppSGA.alojamiento.buscarApartamento(txtCodigo.getText().trim()) != null) {
            AppSGA.mostrarError("Ya existe un apartamento con ese código.");
            return;
        }

        Apartamento apto = new Apartamento(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                dormitorios, capacidad, baja, media, alta);
        apto.actualizarDatos(apto.getNombre(), dormitorios, capacidad, leerCaracteristicas());
        AppSGA.alojamiento.registrarApartamento(apto);
        limpiar();
        refrescar();
        AppSGA.mostrarMensaje("Apartamento creado.");
    }

    private Apartamento seleccionado() {
        int indice = lista.getSelectionModel().getSelectedIndex();
        if (indice < 0) {
            return null;
        }
        return AppSGA.alojamiento.getListaApartamentos().get(indice);
    }

    @FXML
    private void cargarSeleccionado() {
        Apartamento apto = seleccionado();
        if (apto == null || !esAdmin) {
            return;
        }
        txtCodigo.setText(apto.getCodigo());
        txtNombre.setText(apto.getNombre());
        txtDormitorios.setText(String.valueOf(apto.getNumeroDormitorios()));
        txtCapacidad.setText(String.valueOf(apto.getCapacidadMaxima()));
        txtCaracteristicas.setText(String.join(", ", apto.getCaracteristicas()));
        txtTarifaBaja.setText(String.valueOf(apto.getTarifaBaja()));
        txtTarifaMedia.setText(String.valueOf(apto.getTarifaMedia()));
        txtTarifaAlta.setText(String.valueOf(apto.getTarifaAlta()));
    }

    @FXML
    private void modificar() {
        Apartamento apto = seleccionado();
        if (apto == null) {
            AppSGA.mostrarError("Seleccione un apartamento de la lista.");
            return;
        }
        int dormitorios = AppSGA.leerNumero(txtDormitorios.getText());
        int capacidad = AppSGA.leerNumero(txtCapacidad.getText());
        boolean datosOk = apto.actualizarDatos(txtNombre.getText().trim(), dormitorios, capacidad, leerCaracteristicas());
        boolean tarifasOk = apto.actualizarTarifas(AppSGA.leerNumero(txtTarifaBaja.getText()),
                AppSGA.leerNumero(txtTarifaMedia.getText()), AppSGA.leerNumero(txtTarifaAlta.getText()));
        if (!datosOk || !tarifasOk) {
            AppSGA.mostrarError("Revise los datos: los números deben ser mayores que 0.");
            return;
        }
        limpiar();
        refrescar();
        AppSGA.mostrarMensaje("Apartamento modificado. Las reservas ya creadas conservan su valor.");
    }

    @FXML
    private void eliminar() {
        Apartamento apto = seleccionado();
        if (apto == null) {
            AppSGA.mostrarError("Seleccione un apartamento de la lista.");
            return;
        }
        apto.desactivar();
        refrescar();
        AppSGA.mostrarMensaje("Apartamento eliminado (queda inactivo, no se borra).");
    }

    @FXML
    private void cambiarEstado() {
        Apartamento apto = seleccionado();
        if (apto == null || cbEstado.getValue() == null) {
            AppSGA.mostrarError("Seleccione un apartamento y el nuevo estado.");
            return;
        }
        if (!apto.cambiarEstado(cbEstado.getValue(), txtMotivo.getText())) {
            AppSGA.mostrarError("Para dejarlo fuera de servicio debe escribir el motivo.");
            return;
        }
        txtMotivo.clear();
        refrescar();
    }

    @FXML
    private void registrarNovedad() {
        Apartamento apto = seleccionado();
        if (apto == null || cbNovedad.getValue() == null || txtNovedad.getText().isBlank()) {
            AppSGA.mostrarError("Seleccione un apartamento, el tipo de novedad y escriba la descripción.");
            return;
        }
        apto.registrarNovedad(new Novedad(cbNovedad.getValue(), txtNovedad.getText().trim(), LocalDate.now()));
        txtNovedad.clear();
        refrescar();
        AppSGA.mostrarMensaje("Novedad registrada.");
    }

    @FXML
    private void agregarBloqueo() {
        Apartamento apto = seleccionado();
        if (apto == null || dpBloqueoInicio.getValue() == null || dpBloqueoFin.getValue() == null
                || txtMotivoBloqueo.getText().isBlank()) {
            AppSGA.mostrarError("Seleccione un apartamento, las fechas y el motivo del bloqueo.");
            return;
        }
        Bloqueo bloqueo = new Bloqueo(dpBloqueoInicio.getValue(), dpBloqueoFin.getValue(), txtMotivoBloqueo.getText().trim());
        if (!apto.agregarBloqueo(bloqueo)) {
            AppSGA.mostrarError("La fecha final debe ser posterior a la inicial.");
            return;
        }
        txtMotivoBloqueo.clear();
        refrescar();
        AppSGA.mostrarMensaje("Bloqueo agregado.");
    }

    @FXML
    private void agregarTemporada() {
        if (cbTemporada.getValue() == null || dpTemporadaInicio.getValue() == null || dpTemporadaFin.getValue() == null) {
            AppSGA.mostrarError("Seleccione la temporada y las fechas.");
            return;
        }
        if (dpTemporadaFin.getValue().isBefore(dpTemporadaInicio.getValue())) {
            AppSGA.mostrarError("La fecha final no puede ser antes de la inicial.");
            return;
        }
        AppSGA.temporadas.add(new PeriodoTemporada(cbTemporada.getValue(), dpTemporadaInicio.getValue(), dpTemporadaFin.getValue()));
        refrescar();
    }

    private void limpiar() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDormitorios.clear();
        txtCapacidad.clear();
        txtCaracteristicas.clear();
        txtTarifaBaja.clear();
        txtTarifaMedia.clear();
        txtTarifaAlta.clear();
    }

    @Override
    public void refrescar() {
        lista.getItems().clear();
        ArrayList<Apartamento> apartamentos = AppSGA.alojamiento.getListaApartamentos();
        for (int i = 0; i < apartamentos.size(); i++) {
            Apartamento a = apartamentos.get(i);
            String texto = a.getCodigo() + " | " + a.getNombre() + " | " + a.getNumeroDormitorios() + " dorm. | cap. "
                    + a.getCapacidadMaxima() + " | $" + a.getTarifaBaja() + " / $" + a.getTarifaMedia() + " / $"
                    + a.getTarifaAlta() + " | " + a.getEstado();
            if (a.getEstado() == EstadoApartamento.FUERA_SERVICIO) {
                texto += " (" + a.getMotivoFueraServicio() + ")";
            }
            texto += " | novedades: " + a.contarNovedades() + " | bloqueos: " + a.getBloqueos().size();
            if (!a.isActivo()) {
                texto += " | ELIMINADO";
            }
            lista.getItems().add(texto);
        }

        listaTemporadas.getItems().clear();
        for (int i = 0; i < AppSGA.temporadas.size(); i++) {
            PeriodoTemporada p = AppSGA.temporadas.get(i);
            listaTemporadas.getItems().add(p.tipoTemporada() + ": " + p.fechaInicio() + " a " + p.fechaFin());
        }
    }
}
