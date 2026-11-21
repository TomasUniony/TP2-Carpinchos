package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.PriorityQueue;

/**
 * Representa una agenda de eventos programados, que se ejecutan en un paso de simulación determinado.
 * Los eventos se ejecutan en orden de paso y, si empatan, en orden de inserción.
 */
public class Agenda {

    private final PriorityQueue<EventoProgramado> eventos = new PriorityQueue<>();
    private long contadorOrden = 0;

    /**
     * Programa una accion para que se ejecute en un paso futuro.
     *
     * @param paso   paso en el que debe ejecutarse
     * @param accion lo que hay que hacer cuando llegue ese paso
     * @throws IllegalArgumentException si el paso es negativo o la accion es nula
     */
    public void programar(long paso, Runnable accion) {
        if (paso < 0) {
            throw new IllegalArgumentException("Paso invalido: " + paso);
        }
        if (accion == null) {
            throw new IllegalArgumentException("La accion no puede ser nula");
        }
        this.eventos.add(new EventoProgramado(paso, this.contadorOrden, accion));
        this.contadorOrden++;
    }

    /**
     * Ejecuta, en orden, todos los eventos cuyo paso es menor o igual al actual.
     * Si un evento programa otro que tambien ya vencio, ese tambien se ejecuta.
     *
     * @param pasoActual paso en el que esta el juego
     */
    public void ejecutarHasta(long pasoActual) {
        while (!this.eventos.isEmpty() && this.eventos.peek().paso() <= pasoActual) {
            this.eventos.poll().accion().run();
        }
    }

    /** Indica si no quedan eventos pendientes. */
    public boolean estaVacia() {
        return this.eventos.isEmpty();
    }

    /** Cantidad de eventos pendientes. */
    public int cantidad() {
        return this.eventos.size();
    }
}