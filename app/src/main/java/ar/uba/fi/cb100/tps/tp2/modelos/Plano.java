package ar.uba.fi.cb100.tps.tp2.modelos;

public class Plano {
    private final char[][] grilla;
    private boolean salida;
    private Posicion posicionSalida;
    private Posicion posicionHeroe;

    public Plano(String[] plano) {
        if (plano == null || plano.length == 0 || plano[0] == null || plano[0].isBlank()) {
            throw new IllegalArgumentException("El plano no puede estar vacío.");
        }

        this.grilla = new char[plano.length][plano[0].length()];

        //se recorre caractero a caracter para llenar todos los datos en un recorrido solo
        for (int i = 0; i < plano.length; i++) {

            if (plano[i] == null || plano[i].length() != plano[0].length()) {
                throw new IllegalArgumentException("La fila " + i + " es nula o no coincide en columnas.");
            }

            for (int j = 0; j < plano[0].length(); j++) {
                char c = plano[i].charAt(j);
                this.grilla[i][j] = c;

                //si se encuentran, se agrega en el estado
                if (c == 'E') {
                    this.posicionHeroe = new Posicion(i, j);
                } else if (c == 'S') {
                    this.posicionSalida = new Posicion(i, j);
                    salida = true;
                }
            }
        }

        if (this.posicionHeroe == null) {
            throw new IllegalArgumentException("El plano debe contener una celda de entrada ('E').");
        }
    }

    public char caracter(int fil, int col) {
        return grilla[fil][col];
    }

    public int cantidadFilas() {
        return this.grilla.length;
    }

    public int cantidadColumna() {
        return this.grilla[0].length; //todas las columnas tienen la misma longitud
    }

    public Posicion posicionHeroe() { return posicionHeroe; }

    public boolean tieneSalida() { return posicionSalida != null; }

    public Posicion posicionSalida() {
        if (posicionSalida == null) {
            throw new IllegalStateException("Este plano no contiene una casilla de salida ('S').");
        }
        return posicionSalida;
    }
}
