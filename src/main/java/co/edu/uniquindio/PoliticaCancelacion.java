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

    public int calcularDevolucion(int valorEstancia, int totalPagos,
                                  long horasAntelacion, boolean noShow) {
        if (totalPagos <= 0) {
            return 0;
        }
        if (noShow || horasAntelacion < 48) {

            int retencion = valorEstancia;
            int devolucion = totalPagos - retencion;
            return devolucion > 0 ? devolucion : 0;
        } else if (horasAntelacion <= 168) {

            int retencion = (int) (valorEstancia * porcentajeRetencionParcial);
            int devolucion = totalPagos - retencion;
            return devolucion > 0 ? devolucion : 0;
        } else {

            return totalPagos;
        }
    }

}