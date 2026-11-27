package ar.uba.fi.cb100.tps.tp2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import ar.uba.fi.cb100.tps.tp2.persistencia.GestorJson;

//Genera un plano con GeneradorCarpincho a partir de configuracion.json y lo escribe en salida.json.
public class GenerarSalida {

    public static void main(String[] args) throws IOException {
        String resultado = new GeneradorCarpincho().generar(GestorJson.leerTextoConfiguracion());

        String indentado = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create()
                .toJson(JsonParser.parseString(resultado));

        Path ruta = Path.of("src/main/java/ar/uba/fi/cb100/tps/tp2/salida.json");
        Files.writeString(ruta, indentado, StandardCharsets.UTF_8);
        System.out.println("Plano escrito en: " + ruta.toAbsolutePath());
    }
}