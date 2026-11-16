package co.edu.uniquindio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonaTest {

    private final Ocupante ninio = new Ocupante("Sofia", LocalDate.of(2020, 3, 10));


    @DisplayName("Prueba 1: edad un dia antes del cumpleanos")
    @Test
    void edadUnDiaAntesDelCumpleanios() {
        assertEquals(5, ninio.calcularEdad(LocalDate.of(2026, 3, 9)));
    }
    @DisplayName("Prueba 2: edad el dia del cumpleanos")
    @Test
    void edadElDiaDelCumpleanios() {
        assertEquals(6, ninio.calcularEdad(LocalDate.of(2026, 3, 10)));
    }
    @DisplayName("Prueba 3: guarda nombre y fecha de nacimiento")
    @Test
    void guardaNombreYFechaNacimiento() {
        assertEquals("Sofia", ninio.getNombre());
        assertEquals(LocalDate.of(2020, 3, 10), ninio.getFechaNacimiento());
    }
}
