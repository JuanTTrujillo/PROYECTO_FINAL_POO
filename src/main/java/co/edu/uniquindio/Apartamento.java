package co.edu.uniquindio;

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

// Falta: actualizar los datos del apartamento (nombre, dormitorios, capacidad, características)
// Falta: cambiar las tarifas de las tres temporadas
// Falta: agregar un bloqueo y revisar si tiene bloqueo en unas fechas