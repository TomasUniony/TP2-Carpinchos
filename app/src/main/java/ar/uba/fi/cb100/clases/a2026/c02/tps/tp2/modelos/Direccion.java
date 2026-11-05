package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.modelos;

public enum Direccion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    DERECHA(0, 1),
    IZQUIERDA(0, -1);

    private final int deltaFila;
    private final int deltaColumna;

    Direccion(int deltaFila, int deltaColumna) {
        this.deltaFila = deltaFila;
        this.deltaColumna = deltaColumna;
    }

    public int deltaFila() { return this.deltaFila; }

    public int deltaColumna() { return this.deltaColumna; }

    public static Direccion teclaIngresada(char tecla) {
        return switch (Character.toLowerCase(tecla)) {
            case 'w' -> ARRIBA;
            case 's' -> ABAJO;
            case 'a' -> IZQUIERDA;
            case 'd' -> DERECHA;
            default -> null; //Si es otra tecla, devuelve null
        };
    }
}
