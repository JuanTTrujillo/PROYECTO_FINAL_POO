package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class AdministradorTest {

    private Administrador admin;

    @BeforeEach
    public void setUp() {
        admin = new Administrador("Laura Gómez", LocalDate.of(1988, 4, 12), "1094001",
                "3001112233", "laura@apartasuites.com", "admin", "admin123");
    }

    @Test
    public void testCreacionYRolAdministrador() {
        assertEquals(Rol.ADMINISTRADOR, admin.getRol());
        assertTrue(admin.isActivo());
        assertEquals("1094001", admin.getDocumento());
    }

    @Test
    public void testAutenticacionExitosa() {
        assertTrue(admin.autenticar("admin", "admin123"));
    }

    @Test
    public void testAutenticacionFallida() {
        assertFalse(admin.autenticar("usuarioIncorrecto", "admin123"));
        assertFalse(admin.autenticar("admin", "claveIncorrecta"));
    }

    @Test
    public void testDesactivarImpideAutenticacion() {
        admin.desactivar();
        assertFalse(admin.isActivo());
        assertFalse(admin.autenticar("admin", "admin123"));
    }

    @Test
    public void testActualizarDatos() {
        admin.actualizarDatos("Laura Restrepo", "3209998877", "laura.nueva@mail.com");
        assertEquals("Laura Restrepo", admin.getNombre());
        assertEquals("3209998877", admin.getTelefono());
        assertEquals("laura.nueva@mail.com", admin.getCorreo());
    }
}