package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.main;

import ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.modelos.Posicion;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjecutarJuego {

    private static void imprimirTablero(String[] plano, Posicion posHeroe, int energia) {

        System.out.println("\n=== ENERGÍA: " + energia + " ==="); //Muestra el estado del heroe

        for (int i = 0; i < plano.length; i++) {
            String fila = plano[i];
            for (int j = 0; j < fila.length(); j++) {
                if (i == posHeroe.fila() && j == posHeroe.columna()) {
                    System.out.print('E');
                } else {
                    System.out.print(fila.charAt(j)); // Dibuja el carácter real del terreno
                }
            }
            System.out.println(); // Salto de línea al finalizar cada fila
        }
    }

    private static Heroe crearHeroe(String[] plano) {

        for (int i = 0; i < plano.length; i++) {
            String fila = plano[i];
            for (int j = 0; j < fila.length(); j++) {
                if(fila.charAt(j) == 'E') {
                    return new Heroe(i, j, 100);
                }
            }
        }

        throw new RuntimeException("No se encontro la posicion del heore.");
    }

    public static void main(String[] args) throws IOException {

        Path salida = Path.of("src/main/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp2/main/salida.json"); //Json de salida

        String jsonTexto = Files.readString(salida, StandardCharsets.UTF_8); //Lee el json como texto

        //Parsea el JSON y extrae el campo "plano"
        JsonObject jsonObject = JsonParser.parseString(jsonTexto).getAsJsonObject();
        String[] plano = new Gson().fromJson(jsonObject.get("plano"), String[].class);

        Heroe heroe = crearHeroe(plano);

        imprimirTablero(plano, heroe.getPosicion(), heroe.getEnergia());

    }

}
