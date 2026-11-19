package ar.uba.fi.cb100.tps.tp2;
import java.util.Scanner;

public class MenuDeInicio {

    public static boolean mostrarMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=============================");
            System.out.println("       MENÚ PRINCIPAL        ");
            System.out.println("=============================");
            System.out.println("1) Jugar");
            System.out.println("2) Dificultades");
            System.out.println("3) Salir");
            System.out.print("Seleccioná una opción: ");

            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1" -> {
                    System.out.println("\n¡Cargando la mazmorra...");
                    return true; //Sale del menu devuelviendo true para iniciar la partida
                }
                case "2" -> menuDificultades(scanner); //Muestra opciones sin salir del loop
                case "3" -> {
                    System.out.println("\nSaliendo...");
                    return false; //Sale del menu devolviendo false para terminar el programa
                }
                default -> System.out.println("\nOpción inválida.");
            }
        }
    }

    private static void menuDificultades(Scanner scanner) {
        System.out.println("\n--- SELECCIÓN DE DIFICULTAD ---");
        System.out.println("1) Fácil");
        System.out.println("2) Medio");
        System.out.println("3) Difícil");
        System.out.println("4) Volver al Menú Principal");
        System.out.print("Seleccioná una dificultad: ");

        String seleccion = scanner.nextLine().trim();

        switch (seleccion) {
            case "1", "2", "3" -> System.out.println("aun no tiene funcionalidad.");
            case "4" -> System.out.println("Volviendo...");
            default -> System.out.println("Opción invalida.");
        }
    }

}
