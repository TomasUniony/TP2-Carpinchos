package ar.uba.fi.cb100.tps.tp2.modelos;

public record Posicion(int fila, int columna) {

    public Posicion {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("Coordenadas negativas invalidas: (" + fila + ", " + columna + ")");
        }
    }

    public Posicion desplazar(Direccion d) {
        return new Posicion(
                this.fila + d.deltaFila(),
                this.columna + d.deltaColumna()
        );
    }

}
