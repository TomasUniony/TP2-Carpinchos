package ar.uba.fi.cb100.tps.tp2;

import ar.uba.fi.cb100.tps.tp2.modelos.*;
import ar.uba.fi.cb100.tps.tp2.modelos.mundo.Partida;
import ar.uba.fi.cb100.tps.tp2.modelos.mundo.Plano;
import ar.uba.fi.cb100.tps.tp2.modelos.mundo.VistaConsola;
import ar.uba.fi.cb100.tps.tp2.persistencia.GestorJson;
import java.util.Scanner;

public class EjecutarJuego {

    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Configuracion configuracionInicial = GestorJson.obtenerConfiguracionInicial(); //lee la configuracion inicial del juego
        Configuracion configuracionPartida = MenuDeInicio.mostrarMenu(scanner, configuracionInicial); //carga el menu de inicio, donde se pueden cambiar los parametros

        if (configuracionPartida == null) { //se elige no jugar la partida, configuracionPartida es null
            System.out.println("Partida cancelada.");
            return;
        }

        GeneradorDeLaberinto generador = new GeneradorCarpincho(); //se instancia el generador y la partida
        Partida partida = new Partida(generador, configuracionPartida);



        bucleDeJuego(scanner, partida);
    }

    private static void bucleDeJuego(Scanner scanner, Partida partida) {
        while (!partida.finalizada()) {
            Plano planoActual = partida.planoActual();
            Heroe heroe = partida.heroe();

            VistaConsola.imprimirTablero(planoActual, heroe.posicion(), heroe.energia()); //se imprime la vista por consola

            System.out.println("\nIngrese movimiento (w: arriba, s: abajo, a: izquierda, d: derecha): ");

            String entrada = scanner.nextLine().trim().toLowerCase(); //el usuario escribe la entrada

            partida.ejecutarTurno(entrada);
        }

        if (partida.victoria()) {
            System.out.println("\n¡Lograste escapar de la mazmorra!");
        } else {
            System.out.println("\n¡Has perdido! Te quedaste sin energía.");
        }
    }

}
