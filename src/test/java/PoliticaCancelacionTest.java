package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PoliticaCancelacionTest {

    private PoliticaCancelacion politica;

    @BeforeEach
    public void setUp() {
        politica = new PoliticaCancelacion();
    }

    @Test
    public void testCancelacionMasDe7DiasDevolucionCompleta() {
        int estancia = 300000;
        int pagado = 150000;
        long horas = 200; // > 168 horas (7 días)

        int devolucion = politica.calcularDevolucion(estancia, pagado, horas, false);
        // Debe devolver el 100% de lo pagado
        assertEquals(150000, devolucion);
    }

    @Test
    public void testCancelacionEntre2y7DiasRetiene50Porciento() {
        int estancia = 200000;
        int pagado = 150000;
        long horas = 72; // 3 días antes (entre 48h y 168h)

        int devolucion = politica.calcularDevolucion(estancia, pagado, horas, false);
        // Retiene 50% de 200.000 = 100.000. Devolución = 150.000 - 100.000 = 50.000
        assertEquals(50000, devolucion);
    }

    @Test
    public void testCancelacionMenosDe48HorasRetiene100Porciento() {
        int estancia = 200000;
        int pagado = 200000;
        long horas = 24; // < 48 horas

        int devolucion = politica.calcularDevolucion(estancia, pagado, horas, false);
        // Retiene 100% de la estancia -> Devolución 0
        assertEquals(0, devolucion);
    }

    @Test
    public void testNoShowRetiene100Porciento() {
        int estancia = 250000;
        int pagado = 250000;

        int devolucion = politica.calcularDevolucion(estancia, pagado, 0, true);
        assertEquals(0, devolucion);
    }

    @Test
    public void testSinPagosPreviosRetornaCero() {
        int devolucion = politica.calcularDevolucion(200000, 0, 300, false);
        assertEquals(0, devolucion);
    }
}