package co.edu.uniquindio;

public record PoliticaCancelacion(
        int diasDevolucionCompleta,
        int diasRetencionParcial,
        double porcentajeRetencionParcial,
        double porcentajeRetencionTotal
) {

    public PoliticaCancelacion() {
        this(7, 2, 0.50, 1.00);
    }
}