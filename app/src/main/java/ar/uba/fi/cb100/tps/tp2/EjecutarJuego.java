package ar.uba.fi.cb100.tps.tp2;

import ar.uba.fi.cb100.tps.tp2.modelos.*;
import ar.uba.fi.cb100.tps.tp2.persistencia.GestorJson;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class EjecutarJuego {

    private static void imprimirTablero(Plano plano, Posicion posHeroe, int energia) {

        System.out.println("\n=== ENERGÍA: " + energia + " ==="); //Muestra el estado del heroe

        for (int i = 0; i < plano.getCantidadFilas(); i++) {
            for (int j = 0; j < plano.getCantidadColumna(); j++) {
                if (i == posHeroe.fila() && j == posHeroe.columna()) {
                    System.out.print('E');
                } else {
                    System.out.print(plano.getCaracter(i, j)); // Dibuja el carácter real del terreno
                }
            }
            System.out.println(); // Salto de línea al finalizar cada fila
        }
    }

    private static Heroe crearHeroe(Plano plano) {

        Posicion posHeroe = plano.getPosicionHeroe();
        return new Heroe(posHeroe, 100);

    }

    private static boolean moverHeroe(String entrada, Heroe heroe, Plano plano) {

        if(entrada.length() != 1) {
            System.out.println("Tecla ingresada inválida.");
            return false;
        }

        Direccion dir = Direccion.teclaIngresada(entrada.charAt(0));
        if (dir == null) {
            System.out.println("Tecla ingresada inválida.");
            return false;
        }

        Posicion posHeroe = heroe.getPosicion();
        Posicion posDestino = new Posicion(
               posHeroe.fila() + dir.deltaFila(),
               posHeroe.columna() + dir.deltaColumna()
        );

        char celdaDestino = plano.getCaracter(posDestino.fila(), posDestino.columna()); //para obtener el caracter de la celda
        Terreno terreno = Terreno.desdeSimbolo(celdaDestino);

        if (!terreno.esTransitable()) { //validar transitabilidad usando el propio enum
            System.out.println("No puedes avanzar a través de una pared.");
            return false;
        }

        heroe.movimiento(dir); //mover al heroe y descontar la energia
        heroe.quitarEnergia(terreno.costoEnergia());

        if (terreno == Terreno.SALIDA) { //si es la salida, devolver true
            System.out.println("¡Lograste escapar!");
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws IOException {

        Path config = Path.of("src/main/java/ar/uba/fi/cb100/tps/tp2/configuracion.json");
        Path salida = Path.of("src/main/java/ar/uba/fi/cb100/tps/tp2/salida.json"); //Json de salida

        String parametrosJson;
        try {
            parametrosJson = Files.readString(config, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + config, e);
        }

        JsonObject datosIniciales = JsonParser.parseString(parametrosJson).getAsJsonObject();

        Dificultad dificultadInicial = Dificultad.desdeNombre(datosIniciales.get("dificultad").getAsString());
        long semillaInicial = datosIniciales.get("semilla").getAsLong();
        Configuracion configuracionInicial = new Configuracion(dificultadInicial, semillaInicial);

        Scanner scanner = new Scanner(System.in);
        Configuracion iniciarPartida = MenuDeInicio.mostrarMenu(scanner, configuracionInicial);


        String planoJson = new GeneradorCarpincho().generar(parametrosJson);

        GestorJson.guardarJson(planoJson, salida);

        //generar el json

        if(!Files.exists(salida)) {
            throw new IllegalArgumentException("No se encontró el archivo de salida JSON");
        }

        if(iniciarPartida != null){

            String jsonTexto = Files.readString(salida, StandardCharsets.UTF_8); //Lee el json como texto

            //Parsea el JSON y extrae el campo "plano"
            JsonObject jsonObject = JsonParser.parseString(jsonTexto).getAsJsonObject();
            String[] planoString = new Gson().fromJson(jsonObject.get("plano"), String[].class);

            Plano plano = new Plano(planoString);

            Heroe heroe = crearHeroe(plano);

            boolean llegoALaSalida = false;

            while (heroe.tieneEnergia() && !llegoALaSalida) {

                imprimirTablero(plano, heroe.getPosicion(), heroe.getEnergia());
                System.out.println("\n Ingrese movimiento (w(arriba), s(abajo), a(izquierda), d(derecha): ");
                System.out.println("fila: " + heroe.getPosicion().fila() + "columna: " + heroe.getPosicion().columna());

                String entrada = scanner.nextLine().trim().toLowerCase();

                llegoALaSalida = moverHeroe(entrada, heroe, plano);
            }

            if (!heroe.tieneEnergia()) {
                System.out.println("¡Has perdido!");
            }
        }
    }

}
