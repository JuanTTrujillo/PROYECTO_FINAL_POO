package co.edu.uniquindio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Alojamiento alojamiento = new Alojamiento("Aparta-Suites UQ");

        Apartamento apto1 = new Apartamento("A101", "Suite Montaña", 2, 4, 80000, 100000, 130000);
        Apartamento apto2 = new Apartamento("A102", "Suite Valle", 3, 6, 70000, 90000, 120000);
        alojamiento.registrarApartamento(apto1);
        alojamiento.registrarApartamento(apto2);

        Administrador admin = new Administrador("Laura Gómez", LocalDate.of(1988, 4, 12), "1094001",
                "3001112233", "laura@apartasuites.com", "admin", "admin123");
        Recepcionista recepcionista = new Recepcionista("Carlos Ruiz", LocalDate.of(1997, 9, 3), "1094002",
                "3004445566", "carlos@apartasuites.com", "recepcion", "recep123");
        alojamiento.registrarEmpleado(admin);
        alojamiento.registrarEmpleado(recepcionista);

        Huesped huesped = new Huesped("Ana Torres", LocalDate.of(1995, 1, 20), "1094100",
                "3107778899", "ana@correo.com", "ana", "ana123");
        alojamiento.registrarHuesped(huesped);

        ArrayList<Ocupante> ocupantes = new ArrayList<>();
        ocupantes.add(new Ocupante("Juan Torres", LocalDate.of(1993, 6, 15)));
        ocupantes.add(new Ocupante("Sofía Torres", LocalDate.of(2022, 3, 8)));

        LocalDate entrada = LocalDate.now().plusDays(10);
        LocalDate salida = entrada.plusDays(2);

        int personasQuePagan = 0;
        if (huesped.calcularEdad(entrada) >= 6) {
            personasQuePagan++;
        }
        for (int i = 0; i < ocupantes.size(); i++) {
            if (ocupantes.get(i).calcularEdad(entrada) >= 6) {
                personasQuePagan++;
            }
        }

        Cotizacion cotizacion = new Cotizacion(apto1, entrada, salida);
        LocalDate noche = entrada;
        while (noche.isBefore(salida)) {
            int tarifa = apto1.consultarTarifa(TipoTemporada.BAJA);
            cotizacion.agregarNoche(new DetalleNoche(noche, TipoTemporada.BAJA, tarifa, personasQuePagan));
            noche = noche.plusDays(1);
        }

        System.out.println("=== " + alojamiento.getNombre() + " ===");
        System.out.println("Cotización " + apto1.getNombre() + ": " + cotizacion.getCantidadNoches()
                + " noches, " + personasQuePagan + " personas pagan, total $" + cotizacion.getTotal());

        Reserva reserva = new ReservaPortal("R001", apto1, huesped, ocupantes, entrada, salida,
                LocalDateTime.now(), cotizacion.getTotal(), new PoliticaCancelacion());
        alojamiento.registrarReserva(reserva);
        System.out.println("Reserva " + reserva.getCodigo() + " creada en estado " + reserva.getEstado());

        int anticipo = reserva.calcularAnticipoRequerido();
        reserva.getCuenta().registrarPago(new Pago(anticipo, LocalDate.now(), "Anticipo 30%", MedioPago.TARJETA));
        System.out.println("Anticipo pagado: $" + anticipo);

        if (reserva.puedeConfirmarse()) {
            reserva.cambiarEstado(EstadoReserva.CONFIRMADA);
        }
        System.out.println("Estado: " + reserva.getEstado() + " | Saldo pendiente: $" + reserva.calcularSaldo());

        boolean choca = reserva.chocaCon(entrada.plusDays(1), entrada.plusDays(3));
        if (choca) {
            System.out.println("Reserva externa rechazada: el apartamento ya está ocupado esas noches");
        }

        System.out.println("Devolución si cancela hoy: $" + reserva.calcularDevolucion(LocalDateTime.now()));

        Reporte reporte = new Reporte(alojamiento.getListaReservas(), alojamiento.getListaApartamentos());
        System.out.println("Reservas por portal: " + reporte.contarReservasPorCanal(TipoCanalReserva.PORTAL));
        System.out.println("Reservas con saldo pendiente: " + reporte.listarSaldosPendientes().size());
    }
}