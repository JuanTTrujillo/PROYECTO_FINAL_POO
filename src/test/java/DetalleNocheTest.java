package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DetalleNocheTest {

@DisplayName("Prueba 1: subtotal = tarifa x personas que pagan")
    @Test
    void subtotalEsTarifaPorPersonasQuePagan() {
        DetalleNoche d = new DetalleNoche(LocalDate.of(2026, 3, 10), TipoTemporada.BAJA, 50000, 3);
        assertEquals(150000, d.calcularSubtotal());
    }
@DisplayName("Prueba 2: subtotal cuando nadie paga")
    @Test
    void siNadiePagaElSubtotalEsCero() {
        DetalleNoche d = new DetalleNoche(LocalDate.of(2026, 3, 10), TipoTemporada.ALTA, 90000, 0);
        assertEquals(0, d.calcularSubtotal());
    }
    @DisplayName("Prueba 3: guarda los datos recibidos")
    @Test
    void guardaLosDatosRecibidos() {
        DetalleNoche d = new DetalleNoche(LocalDate.of(2026, 3, 10), TipoTemporada.MEDIA, 70000, 2);
        assertEquals(LocalDate.of(2026, 3, 10), d.noche());
        assertEquals(TipoTemporada.MEDIA, d.temporada());
        assertEquals(70000, d.tarifa());
        assertEquals(2, d.personasQuePagan());
    }
}