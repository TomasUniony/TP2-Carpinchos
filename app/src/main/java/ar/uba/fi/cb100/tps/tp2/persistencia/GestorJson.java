package ar.uba.fi.cb100.tps.tp2.persistencia;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import ar.uba.fi.cb100.tps.tp2.modelos.Configuracion;
import ar.uba.fi.cb100.tps.tp2.modelos.Dificultad;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GestorJson {

    // Constructor privado para evitar que se instancie una clase utilitaria
    private GestorJson() {
        throw new UnsupportedOperationException("Clase utilitaria de persistencia");
    }

    /**
     * Formatea un String JSON con pretty printing y lo escribe en disco.
     * Crea los directorios padres si no existen.
     */

    public static Configuracion obtenerConfiguracionInicial () {
        Path config = Path.of("configuracion.json");

        String parametrosJson;

        try {
            parametrosJson = Files.readString(config, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + config, e);
        }

        JsonObject datosIniciales = JsonParser.parseString(parametrosJson).getAsJsonObject();

        Dificultad dificultadInicial = Dificultad.desdeNombre(datosIniciales.get("dificultad").getAsString());

        Long semillaInicial = (datosIniciales.has("semilla") && !datosIniciales.get("semilla").isJsonNull())
                ? datosIniciales.get("semilla").getAsLong() : null; //Por si se recibe una semilla null de base.

        return new Configuracion(dificultadInicial, semillaInicial); //Creo la dificultad inicial, para poder modificarla
    }

    public static void guardarJson(String json, Path destino) {
        if (json == null || destino == null) {
            throw new IllegalArgumentException("El contenido JSON y la ruta de destino no pueden ser nulos");
        }

        // Formatea el JSON para que quede tabulado.

        String indentado = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(JsonParser.parseString(json));
        /*
        1.new GsonBuilder(): Crea un constructor configurable para instanciar el motor de la librería Gson.
        2.setPrettyPrinting(): Activa la sangría y los saltos de línea en la cadena resultante.
        3.serializeNulls(): Configura el serializador para que incluya explícitamente las claves cuyos valores sean
        nulos (por ejemplo "salida": null) en lugar de omitirlas.
        4.create(): Construye e instancia la plantilla final configurada de Gson.
        5.JsonParser.parseString(json): Parsea la cadena cruda json convirtiéndola temporalmente en una
        estructura de objetos en memoria (JsonElement).
        6.toJson(...): Convierte esa estructura de nuevo a un String aplicando todas las reglas configuradas
        (formato indentado e inclusión de nulos).
        7.String indentado = Almacena el resultado formateado en la variable local indentado.
         */

        try {
            Path carpeta = destino.getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            Files.writeString(destino, indentado, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar el archivo JSON en: " + destino, e);
        }
    }

    public static String modificarJson(String json, Path ruta, Configuracion configuracion) throws IOException { //devuelve el json reescrito

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject(); //parsear el texto a JsonObject

        jsonObject.addProperty("dificultad", configuracion.dificultad().nombreDificultad()); //cambia la dificultad

        if (configuracion.semilla() != null) { //cambia la semilla
            jsonObject.addProperty("semilla", configuracion.semilla());
        } else {
            jsonObject.addProperty("semilla", new Random().nextLong()); //crea un long random para no generar problemas en el generador.
        }

        String jsonFormateado = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(jsonObject); //formatea el json

        Files.writeString(ruta, jsonFormateado, StandardCharsets.UTF_8); //reescribe configuracion.json

        return jsonFormateado; //devuelve el json para no tener que llamarlo nuevamente
    }
}