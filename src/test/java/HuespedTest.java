package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HuespedTest {

    private Huesped h;

    @BeforeEach
    void preparar() {
        h = new Huesped("Ana", LocalDate.of(1995, 5, 20), "123", "3001112222",
                "ana@mail.com", "ana", "clave1");
    }
@DisplayName("Prueba 1: autenticar con datos correctos")
    @Test
    void autenticarConDatosCorrectos() {
        assertTrue(h.autenticar("ana", "clave1"));
    }
    @DisplayName("Prueba 2: autenticar con datos incorrectos")
    @Test
    void autenticarConUsuarioOContrasenaMalaFalla() {
        assertFalse(h.autenticar("otro", "clave1"));
        assertFalse(h.autenticar("ana", "mala"));
    }
    @DisplayName("Prueba 3: rol del huesped")
    @Test
    void rolEsHuesped() {
        assertEquals(Rol.HUESPED, h.getRol());
    }
    @DisplayName("Prueba 4: foto vacia al crear el huesped")
    @Test
    void fotoEmpiezaNula() {
        assertNull(h.getRutaFoto());
    }
    @DisplayName("Prueba 5: actualizar perfil")
    @Test
    void actualizarPerfilCambiaLosDatos() {
        h.actualizarPerfil("Ana Maria", "3009998888", "nuevo@mail.com", "foto.png");
        assertEquals("Ana Maria", h.getNombre());
        assertEquals("3009998888", h.getTelefono());
        assertEquals("nuevo@mail.com", h.getCorreo());
        assertEquals("foto.png", h.getRutaFoto());
        assertEquals("123", h.getDocumento()); // el documento no cambia
    }
}