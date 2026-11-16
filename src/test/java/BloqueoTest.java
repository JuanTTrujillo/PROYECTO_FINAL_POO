package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BloqueoTest {

    private final Bloqueo bloqueo =
            new Bloqueo(LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 15), "Mantenimiento");
@DisplayName("Prueba 1: estancia dentro del bloqueo")
    @Test
    void estanciaDentroDelBloqueoChoca() {
        assertTrue(bloqueo.chocaCon(LocalDate.of(2026, 3, 11), LocalDate.of(2026, 3, 13)));
    }
    @DisplayName("Prueba 2: estancia que cruza el inicio")
    @Test
    void estanciaQueCruzaElInicioChoca() {
        assertTrue(bloqueo.chocaCon(LocalDate.of(2026, 3, 8), LocalDate.of(2026, 3, 11)));
    }
    @DisplayName("Prueba 3: sale el dia que empieza el bloqueo")
    @Test
    void estanciaQueSaleElDiaQueEmpiezaElBloqueoNoChoca() {
        assertFalse(bloqueo.chocaCon(LocalDate.of(2026, 3, 8), LocalDate.of(2026, 3, 10)));
    }
    @DisplayName("Prueba 4: entra el dia que termina el bloqueo")
    @Test
    void estanciaQueEntraElDiaQueTerminaElBloqueoNoChoca() {
        assertFalse(bloqueo.chocaCon(LocalDate.of(2026, 3, 15), LocalDate.of(2026, 3, 17)));
    }
@DisplayName("Prueba 5: estancia que cubre todo el bloqueo")
    @Test
    void estanciaQueCubreTodoElBloqueoChoca() {
        assertTrue(bloqueo.chocaCon(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 30)));
    }
}