package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.ArrayList;
import java.util.List;

public class Plano {
    private final Terreno[][] grilla;
    private boolean salida;
    private Posicion posicionSalida;
    private Posicion posicionHeroe;

    public Plano(String[] plano) {
        if (plano == null || plano.length == 0 || plano[0] == null || plano[0].isBlank()) {
            throw new IllegalArgumentException("El plano no puede estar vacío.");
        }

        this.grilla = new Terreno[plano.length][plano[0].length()];

        //se recorre caractero a caracter para llenar todos los datos en un recorrido solo
        for (int i = 0; i < plano.length; i++) {

            if (plano[i] == null || plano[i].length() != plano[0].length()) {
                throw new IllegalArgumentException("La fila " + i + " es nula o no coincide en columnas.");
            }

            for (int j = 0; j < plano[0].length(); j++) {
                char c = plano[i].charAt(j);
                this.grilla[i][j] = Terreno.desdeSimbolo(c);

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

    public Terreno terreno(Posicion posicion) {
        return grilla[posicion.fila()][posicion.columna()];
    }

    public int cantidadFilas() {
        return this.grilla.length;
    }

    public int cantidadColumna() {
        return this.grilla[0].length; //todas las columnas tienen la misma longitud
    }

    public Posicion posicionHeroe() { return posicionHeroe; }

    public boolean tieneSalida() { return salida; }

    public Posicion posicionSalida() {
        if (posicionSalida == null) {
            throw new IllegalStateException("Este plano no contiene una casilla de salida ('S').");
        }
        return posicionSalida;
    }

    public boolean esTransitable(Posicion posicion) { //para facilitar la llamada a esTransitable de terreno
        return this.grilla[posicion.fila()][posicion.columna()].esTransitable();
    }

    public Posicion buscarCeldaLibreCoincidente(Plano otroPlano) {
        int filas = cantidadFilas();
        int columnas = cantidadColumna();

        Posicion posicionFallback = null;
        Plano planoAAbrir = null; // Guarda la referencia del plano donde habrá que romper la pared

        for (int f = 1; f < filas - 1; f++) {
            for (int c = 1; c < columnas - 1; c++) {
                Posicion posicion = new Posicion(f, c);

                Terreno terrenoOrigen = terreno(posicion);
                Terreno terrenoDestino = otroPlano.terreno(posicion);

                if (terrenoOrigen.esLibreParaConexion() && terrenoDestino.esLibreParaConexion()) {
                    return posicion;  //si en ambos planos la posicion esta libre, lo devuelve
                }

                if (posicionFallback == null) { //guarda el primer candidata a romper pared si no lo hay.
                    if (terrenoOrigen.esLibreParaConexion() && terrenoDestino == Terreno.PARED) {
                        posicionFallback = posicion;
                        planoAAbrir = otroPlano;
                    } else if (terrenoDestino.esLibreParaConexion() && terrenoOrigen == Terreno.PARED) {
                        posicionFallback = posicion;
                        planoAAbrir = this; //plano actual
                    }
                }
            }
        }

        if (posicionFallback != null) { //si se encontro una celda libre, se rompe pared del plano a abrir y se devuelve la posicion
            planoAAbrir.cambiarTerreno(posicionFallback, Terreno.PISO);
            return posicionFallback;
        }

        throw new IllegalStateException("No fue posible encontrar ni abrir una celda coincidente entre los dos planos.");
    }

    public void cambiarTerreno(Posicion posicion, Terreno terreno) {
        this.grilla[posicion.fila()][posicion.columna()] = terreno;
    }

    public List<Posicion> obtenerCandidatoBordeIzquierdo() {
        List<Posicion> candidatas = new ArrayList<>(); //se utiliza una lista para elegir una al azar y que no sea siempre la superior izquierda/derecha
        int filas = cantidadFilas();

        for (int f = 1; f < filas - 1; f++) {
            cargarCandidataBorde(new Posicion(f, 0), new Posicion(f, 1), candidatas);
        }

        return candidatas;
    }

    public List<Posicion> obtenerCandidatoBordeDerecho() {
        List<Posicion> candidatas = new ArrayList<>();
        int filas = cantidadFilas();
        int colDerecha = cantidadColumna() - 1;

        for (int f = 1; f < filas - 1; f++) {
            cargarCandidataBorde(new Posicion(f, colDerecha), new Posicion(f, colDerecha - 1), candidatas);
        }

        return candidatas;
    }

    private void cargarCandidataBorde(Posicion posicionBorde, Posicion posicionInterior, List<Posicion> candidatas) {
        Terreno terrenoInterior = terreno(posicionInterior);
        Terreno terrenoBorde = terreno(posicionBorde);

        //la celda de borde debe estar libre (no tener otra conexión previa) y la celda interior contigua debe ser transitable (no ser pared)
        if (terrenoBorde.esLibreParaConexion() && terrenoInterior.esTransitable() && terrenoInterior != Terreno.PARED) {
            candidatas.add(posicionBorde);
        }
    }
    
    /** Indica si la posicion cae dentro de la grilla.
     *  @param posicion posicion a verificar
     * @return true si la posicion esta dentro de la grilla, false si esta
     *  fuera de los limites del plano
     */
    public boolean contiene(Posicion posicion) {
        return posicion.fila() >= 0 && posicion.fila() < this.cantidadFilas()
            && posicion.columna() >= 0 && posicion.columna() < this.cantidadColumna();
    }

    /**
     * Indica si la posicion es un borde del plano (fila 0, fila maxima, columna 0 o columna maxima).
     * @param posicion posicion a verificar
     * @return true si la posicion es un borde del plano, false si no lo es
     */
    public boolean esBorde(Posicion posicion) {
        return posicion.fila() == 0 || posicion.fila() == this.cantidadFilas() - 1
            || posicion.columna() == 0 || posicion.columna() == this.cantidadColumna() - 1;
    }

}
