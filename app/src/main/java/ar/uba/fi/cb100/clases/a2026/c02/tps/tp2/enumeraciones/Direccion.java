package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.enumeraciones;

/**
 * Representa las cuatro direcciones posibles para recorrer una matriz.
 */
public enum Direccion {
    ARRIBA(-1, 0), ABAJO(1, 0), IZQUIERDA(0, -1), DERECHA(0, 1);

    private final int desplazamientoFila;
    private final int desplazamientoColumna;

    /**
     * Construye una dirección con sus desplazamientos de fila y columna.
     * 
     * @param desplazamientoFila    desplazamiento de fila.
     * @param desplazamientoColumna desplazamiento de columna.
     */
    Direccion(int desplazamientoFila, int desplazamientoColumna) {
        this.desplazamientoFila = desplazamientoFila;
        this.desplazamientoColumna = desplazamientoColumna;
    }

    /**
     * Devuelve el desplazamiento de fila de la dirección.
     * 
     * @return el desplazamiento de la fila.
     */
    public int getDesplazamientoFila() {
        return desplazamientoFila;
    }

    /**
     * Devuelve el desplazamiento de columna de la dirección.
     * 
     * @return el desplazamiento de columna.
     */
    public int getDesplazamientoColumna() {
        return desplazamientoColumna;
    }
}
