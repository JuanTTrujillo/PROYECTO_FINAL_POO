package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RecepcionistaTest {

    private Recepcionista r;

    @BeforeEach
    void preparar() {
        r = new Recepcionista("Luis", LocalDate.of(1990, 1, 1), "456", "3101112222",
                "luis@mail.com", "luis", "pass1");
    }
@DisplayName("Prueba 1: rol del recepcionista")
    @Test
    void rolEsRecepcionista() {
        assertEquals(Rol.RECEPCIONISTA, r.getRol());
    }
    @DisplayName("Prueba 2: empleado nuevo esta activo")
    @Test
    void nuevoEmpleadoEstaActivo() {
        assertTrue(r.isActivo());
    }
    @DisplayName("Prueba 3: autenticar correcto")
    @Test
    void autenticarCorrecto() {
        assertTrue(r.autenticar("luis", "pass1"));
    }
    @DisplayName("Prueba 4: autenticar incorrecto")
    @Test
    void autenticarIncorrectoFalla() {
        assertFalse(r.autenticar("luis", "mala"));
        assertFalse(r.autenticar("otro", "pass1"));
    }
    @DisplayName("Prueba 5: empleado desvinculado no se autentica")
    @Test
    void desvinculadoNoPuedeAutenticarse() {
        r.desactivar();
        assertFalse(r.isActivo());
        assertFalse(r.autenticar("luis", "pass1"));
    }
    @DisplayName("Prueba 6: actualizar datos del empleado")
    @Test
    void actualizarDatosCambiaNombreTelefonoYCorreo() {
        r.actualizarDatos("Luis Perez", "3205556666", "lp@mail.com");
        assertEquals("Luis Perez", r.getNombre());
        assertEquals("3205556666", r.getTelefono());
        assertEquals("lp@mail.com", r.getCorreo());
    }
}