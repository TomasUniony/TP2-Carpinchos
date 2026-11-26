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

    public boolean tieneSalida() { return salida; }

    public Posicion posicionSalida() {
        if (posicionSalida == null) {
            throw new IllegalStateException("Este plano no contiene una casilla de salida ('S').");
        }
        return posicionSalida;
    }

    public Posicion buscarCeldaLibreCoincidente(Plano otroPlano) {
        int filas = cantidadFilas();
        int columnas = cantidadColumna();

        //busca coincidencia directa de celdas libres en ambos planos
        for (int f = 1; f < filas - 1; f++) { //empieza en 1 y termina en -1, porque no nos sirve recorrer los bordes del mapa
            for (int c = 1; c < columnas - 1; c++) {
                Posicion pos = new Posicion(f, c);

                Terreno terrenoOrigen = Terreno.desdeSimbolo(caracter(f, c)); //crea el objeto terreno en este plano
                Terreno terrenoDestino = Terreno.desdeSimbolo(otroPlano.caracter(f, c));//crea el objeto terreno para el plano a comparar

                if (terrenoOrigen.esLibreParaConexion() && terrenoDestino.esLibreParaConexion()) {
                    return pos;
                }
            }
        }

        //busca una celda libre en un plano y pared en el otro, y convierte la pared en piso

        for (int f = 1; f < filas - 1; f++) {
            for (int c = 1; c < columnas - 1; c++) {
                Posicion pos = new Posicion(f, c);

                Terreno terrenoOrigen = Terreno.desdeSimbolo(caracter(f, c)); //crea el objeto terreno en este plano
                Terreno terrenoDestino = Terreno.desdeSimbolo(otroPlano.caracter(f, c));

                if (terrenoOrigen.esLibreParaConexion() && terrenoDestino == Terreno.PARED) {
                    otroPlano.convertirAPiso(pos); //rompe pared en plano destino
                    return pos;
                } else if (terrenoDestino.esLibreParaConexion() && terrenoOrigen == Terreno.PARED) {
                    this.convertirAPiso(pos); //rompe pared en este plano
                    return pos;
                }
            }
        }

        throw new IllegalStateException("No fue posible encontrar ni abrir una celda coincidente entre los dos planos.");
    }

    public void crearEscalera(Posicion posicion) {
        this.grilla[posicion.fila()][posicion.columna()] = Terreno.ESCALERA.simbolo();
    }

    public void crearPasillo(Posicion posicion) {
        this.grilla[posicion.fila()][posicion.columna()] = Terreno.PASILLO.simbolo();
    }

    private void convertirAPiso(Posicion pos) {
        this.grilla[pos.fila()][pos.columna()] = Terreno.PISO.simbolo();
    }

}
