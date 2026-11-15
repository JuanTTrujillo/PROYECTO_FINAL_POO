package co.edu.uniquindio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Apartamento implements Desactivable {

    private String codigo;
    private String nombre;
    private int numeroDormitorios;
    private int capacidadMaxima;
    private ArrayList<String> caracteristicas;
    private int tarifaBaja;
    private int tarifaMedia;
    private int tarifaAlta;
    private EstadoApartamento estado;
    private String motivoFueraServicio;
    private boolean activo;
    private ArrayList<Bloqueo> bloqueos;
    private ArrayList<Novedad> novedades;

    public Apartamento(String codigo, String nombre, int numeroDormitorios, int capacidadMaxima,
                       int tarifaBaja, int tarifaMedia, int tarifaAlta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.numeroDormitorios = numeroDormitorios;
        this.capacidadMaxima = capacidadMaxima;
        this.tarifaBaja = tarifaBaja;
        this.tarifaMedia = tarifaMedia;
        this.tarifaAlta = tarifaAlta;
        this.estado = EstadoApartamento.PREPARADO;
        this.motivoFueraServicio = "";
        this.activo = true;
        this.caracteristicas = new ArrayList<>();
        this.bloqueos = new ArrayList<>();
        this.novedades = new ArrayList<>();
    }

    public int getTarifa(TipoTemporada temporada) {
        if (temporada == TipoTemporada.ALTA) {
            return tarifaAlta;
        } else if (temporada == TipoTemporada.MEDIA) {
            return tarifaMedia;
        } else {
            return tarifaBaja;
        }
    }

    public boolean cambiarEstado(EstadoApartamento nuevo, String motivo) {
        if (nuevo == EstadoApartamento.FUERA_SERVICIO) {
            if (motivo == null || motivo.isEmpty()) {
                return false;
            }
            motivoFueraServicio = motivo;
        } else {
            motivoFueraServicio = "";
        }
        estado = nuevo;
        return true;
    }

    public void registrarNovedad(Novedad n) {
        novedades.add(n);
    }

    public int contarNovedades() {
        return novedades.size();
    }

    @Override
    public void desactivar() {
        activo = false;
    }

    @Override
    public boolean isActivo() {
        return activo;
    }



    public void actualizarDatos(String nombre, int numeroDormitorios, int capacidadMaxima, ArrayList<String> caracteristicas) {
        this.nombre = nombre;
        this.numeroDormitorios = numeroDormitorios;
        this.capacidadMaxima = capacidadMaxima;
        this.caracteristicas = caracteristicas;
    }

    public boolean actualizarTarifas(int tarifaBaja, int tarifaMedia, int tarifaAlta) {
        if (tarifaBaja <= 0 || tarifaMedia <= 0 || tarifaAlta <= 0) {
            return false;
        }
        this.tarifaBaja = tarifaBaja;
        this.tarifaMedia = tarifaMedia;
        this.tarifaAlta = tarifaAlta;
        return true;
    }

    public boolean agregarBloqueo(Bloqueo b) {
        if (!b.fechaFin().isAfter(b.fechaInicio())) {
            return false;
        }
        bloqueos.add(b);
        return true;
    }

    public boolean tieneBloqueoEn(LocalDate entrada, LocalDate salida) {
        for (int i = 0; i < bloqueos.size(); i++) {
            if (bloqueos.get(i).chocaCon(entrada, salida)) {
                return true;
            }
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public EstadoApartamento getEstado() {
        return estado;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroDormitorios() {
        return numeroDormitorios;
    }

    public void setNumeroDormitorios(int numeroDormitorios) {
        this.numeroDormitorios = numeroDormitorios;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getTarifaBaja() {
        return tarifaBaja;
    }

    public void setTarifaBaja(int tarifaBaja) {
        this.tarifaBaja = tarifaBaja;
    }

    public ArrayList<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ArrayList<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getTarifaMedia() {
        return tarifaMedia;
    }

    public void setTarifaMedia(int tarifaMedia) {
        this.tarifaMedia = tarifaMedia;
    }

    public int getTarifaAlta() {
        return tarifaAlta;
    }

    public void setTarifaAlta(int tarifaAlta) {
        this.tarifaAlta = tarifaAlta;
    }

    public String getMotivoFueraServicio() {
        return motivoFueraServicio;
    }

    public void setMotivoFueraServicio(String motivoFueraServicio) {
        this.motivoFueraServicio = motivoFueraServicio;
    }

    public void setEstado(EstadoApartamento estado) {
        this.estado = estado;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ArrayList<Bloqueo> getBloqueos() {
        return bloqueos;
    }

    public void setBloqueos(ArrayList<Bloqueo> bloqueos) {
        this.bloqueos = bloqueos;
    }

    public ArrayList<Novedad> getNovedades() {
        return novedades;
    }

    public void setNovedades(ArrayList<Novedad> novedades) {
        this.novedades = novedades;
    }
}