package ar.uba.fi.cb100.tps.tp2.modelos;

/**
 * Representa un evento programado en el simulador.
 * @param paso El paso de simulación en el que se ejecutará el evento.
 * @param orden El orden de inserción del evento, utilizado para desempatar eventos con el mismo paso.
 * @param accion La acción a ejecutar cuando se alcance el paso de simulación correspondiente
 */
public record EventoProgramado(long paso, long orden, Runnable accion)
        implements Comparable<EventoProgramado> {

    /**
     * Ordena por paso y, si empatan, por orden de insercion.
     */
    @Override
    public int compareTo(EventoProgramado otro) {
        int comparacionPaso = Long.compare(this.paso, otro.paso);
        if (comparacionPaso != 0) {
            return comparacionPaso;
        }
        return Long.compare(this.orden, otro.orden);
    }
}