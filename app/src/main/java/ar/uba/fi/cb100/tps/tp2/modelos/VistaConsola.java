package ar.uba.fi.cb100.tps.tp2.modelos;

public class VistaConsola {

    public static void imprimirTablero(Plano plano, Posicion posHeroe, int energia) {
        System.out.println("\n=== ENERGÍA RESTANTE: " + energia + " ===");

        for (int f = 0; f < plano.cantidadFilas(); f++) {
            for (int c = 0; c < plano.cantidadColumna(); c++) {
                Posicion posActual = new Posicion(f, c);

                if (posActual.equals(posHeroe)) {
                    System.out.print('E'); //dibuja al heroe sobre la grilla visual
                } else {
                    System.out.print(plano.terreno(posActual).simbolo());
                }
            }
            System.out.println();//salto de línea al finalizar cada fila
        }
    }
}