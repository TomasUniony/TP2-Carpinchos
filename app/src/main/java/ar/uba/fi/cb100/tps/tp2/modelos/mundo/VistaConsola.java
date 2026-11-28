package ar.uba.fi.cb100.tps.tp2.modelos.mundo;

import ar.uba.fi.cb100.tps.tp2.modelos.Posicion;

public class VistaConsola {

    public static void imprimirTablero(Plano plano, Posicion posHeroe, int energia) {
        System.out.println("\n=== ENERGÍA RESTANTE: " + energia + " ===");

        for (int f = 0; f < plano.cantidadFilas(); f++) {
            for (int c = 0; c < plano.cantidadColumna(); c++) {
                Posicion posActual = new Posicion(f, c);
                char caracter = plano.celda(posActual).simbolo();

                System.out.print(caracter);
            }
            System.out.println();//salto de línea al finalizar cada fila
        }
    }
}