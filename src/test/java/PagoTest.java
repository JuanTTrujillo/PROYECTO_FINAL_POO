package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    @DisplayName("Prueba 1: registro de un pago")
    @Test
    void guardaMontoFechaDescripcionYMedio() {
        Pago p = new Pago(150000, LocalDate.of(2026, 3, 10), "Anticipo", MedioPago.TRANSFERENCIA);
        assertEquals(150000, p.getMonto());
        assertEquals(LocalDate.of(2026, 3, 10), p.getFecha());
        assertEquals("Anticipo", p.getDescripcion());
        assertEquals(MedioPago.TRANSFERENCIA, p.getMedio());
    }
}