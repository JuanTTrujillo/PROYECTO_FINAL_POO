package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaExternaTest {

    private Apartamento apto;
    private Huesped titular;
    private PoliticaCancelacion politica;

    @BeforeEach
    public void setUp() {
        apto = new Apartamento("A101", "Suite Montaña", 2, 4, 80000, 100000, 130000);
        titular = new Huesped("Ana Torres", LocalDate.of(1995, 1, 20), "1094100",
                "3107778899", "ana@correo.com", "ana", "ana123");
        politica = new PoliticaCancelacion();
    }

    @Test
    public void testCreacionReservaExterna() {
        ReservaExterna res = new ReservaExterna("RES-01", apto, titular, new ArrayList<>(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(5),
                LocalDateTime.now(), 300000, politica, "AIRBNB-555");

        assertEquals("AIRBNB-555", res.getCodigoExterno());
        assertEquals(TipoCanalReserva.EXTERNA, res.getCanal());
        assertEquals(EstadoReserva.PENDIENTE, res.getEstado());
    }

    @Test
    public void testCalcularComision15Porciento() {
        ReservaExterna res = new ReservaExterna("RES-02", apto, titular, new ArrayList<>(),
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(6),
                LocalDateTime.now(), 200000, politica, "BOOKING-101");


        assertEquals(30000, res.calcularComision());
    }

    @Test
    public void testNoRequiereAnticipo() {
        ReservaExterna res = new ReservaExterna("RES-03", apto, titular, new ArrayList<>(),
                LocalDate.now().plusDays(3), LocalDate.now().plusDays(6),
                LocalDateTime.now(), 200000, politica, "EXPEDIA-77");

        assertFalse(res.requiereAnticipo());
        assertEquals(0, res.calcularAnticipoRequerido());
    }

    @Test
    public void testSetCodigoExterno() {
        ReservaExterna res = new ReservaExterna("RES-04", apto, titular, new ArrayList<>(),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                LocalDateTime.now(), 150000, politica, "OLD-CODE");

        res.setCodigoExterno("NEW-CODE-99");
        assertEquals("NEW-CODE-99", res.getCodigoExterno());
    }
}