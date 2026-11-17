package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CuentaPagarTest {

    private CuentaPagar cuenta;

    @BeforeEach
    public void setUp() {
        cuenta = new CuentaPagar();
    }

    @Test
    public void testCuentaInicialVacia() {
        assertEquals(0, cuenta.calcularTotalCargos());
        assertEquals(0, cuenta.calcularTotalPagos());
        assertEquals(0, cuenta.calcularSaldo());
        assertEquals(0, cuenta.getListaCargos().size());
        assertEquals(0, cuenta.getListaPagos().size());
    }

    @Test
    public void testRegistrarCargos() {
        cuenta.registrarCargo(new Cargo(150000, LocalDate.now(), "Alojamiento", TipoCargo.ALOJAMIENTO));
        cuenta.registrarCargo(new Cargo(20000, LocalDate.now(), "Minibar", TipoCargo.SERVICIO_ADICIONAL));

        assertEquals(170000, cuenta.calcularTotalCargos());
        assertEquals(0, cuenta.calcularTotalPagos());
        assertEquals(170000, cuenta.calcularSaldo());
    }

    @Test
    public void testRegistrarPagosYCalculoSaldo() {
        cuenta.registrarCargo(new Cargo(200000, LocalDate.now(), "Alojamiento", TipoCargo.ALOJAMIENTO));
        cuenta.registrarPago(new Pago(60000, LocalDate.now(), "Anticipo 30%", MedioPago.TRANSFERENCIA));

        assertEquals(200000, cuenta.calcularTotalCargos());
        assertEquals(60000, cuenta.calcularTotalPagos());
        assertEquals(140000, cuenta.calcularSaldo());
    }

    @Test
    public void testPagosParcialesHastaCompletarSaldo() {
        cuenta.registrarCargo(new Cargo(100000, LocalDate.now(), "Estancia", TipoCargo.ALOJAMIENTO));
        cuenta.registrarPago(new Pago(40000, LocalDate.now(), "Abono 1", MedioPago.EFECTIVO));
        cuenta.registrarPago(new Pago(60000, LocalDate.now(), "Abono 2", MedioPago.TARJETA));

        assertEquals(100000, cuenta.calcularTotalPagos());
        assertEquals(0, cuenta.calcularSaldo());
    }

    @Test
    public void testIgnorarMovimientosNulos() {
        cuenta.registrarCargo(null);
        cuenta.registrarPago(null);
        assertEquals(0, cuenta.calcularSaldo());
    }
}