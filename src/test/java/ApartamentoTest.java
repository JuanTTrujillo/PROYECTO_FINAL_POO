package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApartamentoTest {

    private Apartamento apto;

    @BeforeEach
    void preparar() {
        apto = new Apartamento("A1", "Vista Montana", 2, 4, 50000, 70000, 90000);
    }
@DisplayName("Prueba 1: registro de un apartamento (estado inicial) ")
    @Test
    void estadoInicialEsPreparadoYActivo() {
        assertEquals(EstadoApartamento.PREPARADO, apto.getEstado());
        assertTrue(apto.isActivo());
    }
    @DisplayName("Prueba 2: consulta de tarifa por temporada")
    @Test
    void getTarifaDevuelveLaDeCadaTemporada() {
        assertEquals(50000, apto.getTarifaBaja());
        assertEquals(70000, apto.getTarifaMedia());
        assertEquals(90000, apto.getTarifaAlta());
    }

    @DisplayName("Prueba 3: fuera de servicio sin motivo")
    @Test
    void fueraServicioSinMotivoSeRechaza() {
        assertFalse(apto.cambiarEstado(EstadoApartamento.FUERA_SERVICIO, ""));
        assertFalse(apto.cambiarEstado(EstadoApartamento.FUERA_SERVICIO, null));
        assertEquals(EstadoApartamento.PREPARADO, apto.getEstado());
    }
    @DisplayName("Prueba 4: fuera de servicio con motivo")
    @Test
    void fueraServicioConMotivoSeAcepta() {
        assertTrue(apto.cambiarEstado(EstadoApartamento.FUERA_SERVICIO, "Dano en tuberia"));
        assertEquals(EstadoApartamento.FUERA_SERVICIO, apto.getEstado());
        assertEquals("Dano en tuberia", apto.getMotivoFueraServicio());
    }
    @DisplayName("Prueba 5: salir de fuera de servicio limpia el motivo")
    @Test
    void salirDeFueraServicioLimpiaMotivo() {
        apto.cambiarEstado(EstadoApartamento.FUERA_SERVICIO, "Mantenimiento");
        apto.cambiarEstado(EstadoApartamento.PREPARADO, null);
        assertEquals("", apto.getMotivoFueraServicio());
    }
    @DisplayName("Prueba 6: actualizar tarifas validas")
    @Test
    void actualizarTarifasValidas() {
        assertTrue(apto.actualizarTarifas(60000, 80000, 100000));
        assertEquals(60000, apto.getTarifaBaja());
        assertEquals(80000, apto.getTarifaMedia());
        assertEquals(100000, apto.getTarifaAlta());
    }
    @DisplayName("Prueba 7: actualizar tarifas invalidas")
    @Test
    void actualizarTarifasInvalidasNoCambiaNada() {
        assertFalse(apto.actualizarTarifas(0, 80000, 100000));
        assertFalse(apto.actualizarTarifas(60000, -1, 100000));
        assertEquals(50000, apto.getTarifaBaja());
    }
    @DisplayName("Prueba 8: actualizar datos validos")
    @Test
    void actualizarDatosValidos() {
        ArrayList<String> carac = new ArrayList<>();
        carac.add("Jacuzzi");
        assertTrue(apto.actualizarDatos("Nuevo", 3, 6, carac));
        assertEquals("Nuevo", apto.getNombre());
        assertEquals(3, apto.getNumeroDormitorios());
        assertEquals(6, apto.getCapacidadMaxima());
        assertEquals(1, apto.getCaracteristicas().size());
    }
    @DisplayName("Prueba 9: actualizar datos invalidos")
    @Test
    void actualizarDatosInvalidosSeRechazan() {
        assertFalse(apto.actualizarDatos("", 3, 6, new ArrayList<>()));
        assertFalse(apto.actualizarDatos("X", 0, 6, new ArrayList<>()));
        assertFalse(apto.actualizarDatos("X", 3, 0, new ArrayList<>()));
        assertEquals("Vista Montana", apto.getNombre());
    }
    @DisplayName("Prueba 10: bloqueo con fechas invalidas")
    @Test
    void bloqueoConFechasInvalidasSeRechaza() {
        Bloqueo malo = new Bloqueo(LocalDate.of(2026, 3, 15), LocalDate.of(2026, 3, 10), "Error");
        assertFalse(apto.agregarBloqueo(malo));
        assertEquals(0, apto.getBloqueos().size());
    }
    @DisplayName("Prueba 11: detectar bloqueo en una estancia")
    @Test
    void tieneBloqueoEnDetectaCruce() {
        apto.agregarBloqueo(new Bloqueo(LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 15), "Pintura"));
        assertTrue(apto.tieneBloqueoEn(LocalDate.of(2026, 3, 12), LocalDate.of(2026, 3, 14)));
        assertFalse(apto.tieneBloqueoEn(LocalDate.of(2026, 3, 15), LocalDate.of(2026, 3, 17)));
        assertFalse(apto.tieneBloqueoEn(LocalDate.of(2026, 3, 8), LocalDate.of(2026, 3, 10)));
    }
    @DisplayName("Prueba 12: desactivar un apartamento")
    @Test
    void desactivarMarcaInactivo() {
        apto.desactivar();
        assertFalse(apto.isActivo());
    }
    @DisplayName("Prueba 13: registrar una novedad")
    @Test
    void registrarNovedadAumentaElContador() {

        apto.registrarNovedad(new Novedad(TipoNovedad.DANIO, "Vidrio roto", LocalDate.of(2026, 3, 10)));
        assertEquals(1, apto.contarNovedades());
    }
}