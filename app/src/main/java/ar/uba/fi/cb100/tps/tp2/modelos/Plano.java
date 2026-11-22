package ar.uba.fi.cb100.tps.tp2.modelos;

public class Plano {
    char[][] grilla;

    public Plano(String[] plano) {
        if (plano == null || plano.length == 0 || plano[0] == null || plano[0].isBlank()) {
            throw new IllegalArgumentException("El plano no puede estar vacío.");
        }

        this.grilla = new char[plano.length][];

        for (int i = 0; i < plano.length; i++) {
            this.grilla[i] = plano[i].toCharArray();
        }
    }

    public char getCaracter(int fil, int col) {
        return grilla[fil][col];
    }

    public int getCantidadFilas() {
        return this.grilla.length;
    }

    public int getCantidadColumna() {
        return this.grilla[0].length; //todas las columnas tienen la misma longitud
    }

    public Posicion getPosicionHeroe() {
        for (int i = 0; i < grilla.length; i++) {
            for (int j = 0; j < grilla[i].length; j++) {
                if (grilla[i][j] == 'E') {
                    return new Posicion(i, j);
                }
            }
        }
        throw new IllegalArgumentException("No se encontro al heroe en el plano.");
    }
}
