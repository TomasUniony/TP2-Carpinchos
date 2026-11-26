package ar.uba.fi.cb100.tps.tp2;

import ar.uba.fi.cb100.tps.tp2.modelos.Configuracion;
import ar.uba.fi.cb100.tps.tp2.modelos.Dificultad;
import ar.uba.fi.cb100.tps.tp2.persistencia.GestorJson;

import java.io.IOException;
import java.util.Scanner;

public class MenuDeInicio {

    public static Configuracion mostrarMenu(Scanner scanner, Configuracion configuracionInicial) {

        Configuracion configuracionFinal = configuracionInicial;

        while (true) {
            System.out.println("\n=============================");
            System.out.println("       MENÚ PRINCIPAL        ");
            System.out.println("=============================");
            System.out.println("1) Jugar");
            System.out.println("2) Dificultades");
            System.out.println("3) Semilla");
            System.out.println("4) Salir");
            System.out.print("Seleccioná una opción: ");

            String entrada = scanner.nextLine().trim();

            switch (entrada) {
                case "1" -> {
                    System.out.println("\n¡Cargando la mazmorra...");

                    if (!configuracionFinal.equals(configuracionInicial)) {  //Compar la config final vs inicial.
                        GestorJson.modificarJson(configuracionFinal);       //Si son distintas, se modifica el json
                    }

                    return configuracionFinal; //Sale del menu devuelviendo true para iniciar la partida
                }
                case "2" -> configuracionFinal = new Configuracion(menuDificultades(scanner, configuracionFinal), configuracionFinal.semilla());//Muestra opciones sin salir del loop
                case "3" -> configuracionFinal = new Configuracion(configuracionFinal.dificultad(), menuSemilla(scanner, configuracionFinal));
                case "4" -> {
                    System.out.println("\nSaliendo...");
                    return null; //Sale del menu devolviendo false para terminar el programa
                }
                default -> System.out.println("\nOpción inválida.");
            }
        }
    }

    private static Long menuSemilla(Scanner scanner, Configuracion configuracion) {
        System.out.println("\n--- SELECCIÓN DE SEMILLA ---");
        System.out.println("Semilla actual: " + configuracion.semilla());
        System.out.println("1) Cambiar semilla");
        System.out.println("2) Conservar semilla");
        System.out.println("3) Quitar semilla");

        String seleccion = scanner.nextLine().trim();

        switch (seleccion) {
            case "1" -> {
                System.out.println("\nInserte la semilla: ");
                try {
                    return Long.parseLong(scanner.nextLine().trim());
                } catch (NumberFormatException e) { //En caso de que no sea convertible
                    System.out.println("Semilla invalida");
                    return configuracion.semilla();
                }
            }
            case "2" -> {
                return configuracion.semilla();
            }
            case "3" -> {
                return null;
            }
            default -> {
                System.out.println("Opción invalida.");
                return configuracion.semilla();
            }
        }
    }

    private static Dificultad menuDificultades(Scanner scanner, Configuracion configuracion) {
        System.out.println("\n--- SELECCIÓN DE DIFICULTAD ---");
        System.out.println("Dificultad actual: " + configuracion.dificultad());
        System.out.println("1) Fácil");
        System.out.println("2) Medio");
        System.out.println("3) Difícil");
        System.out.println("4) Volver al Menú Principal");
        System.out.print("Seleccioná una dificultad: ");

        String seleccion = scanner.nextLine().trim();

        switch (seleccion) {
            case "1" -> {
                return Dificultad.FACIL;
            }
            case "2" -> {
                return Dificultad.MEDIO;
            }
            case "3" -> {
                return Dificultad.DIFICIL;
            }
            case "4" -> {
                return configuracion.dificultad();
            }
            default -> System.out.println("Opción invalida.");
        }
        return configuracion.dificultad();
    }

}
