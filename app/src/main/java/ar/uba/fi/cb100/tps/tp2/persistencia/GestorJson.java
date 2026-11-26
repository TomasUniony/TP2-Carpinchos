package ar.uba.fi.cb100.tps.tp2.persistencia;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import ar.uba.fi.cb100.tps.tp2.modelos.Configuracion;
import ar.uba.fi.cb100.tps.tp2.modelos.Dificultad;
import com.google.gson.*;

public class GestorJson {

    // Constructor privado para evitar que se instancie una clase utilitaria
    private GestorJson() {
        throw new UnsupportedOperationException("Clase utilitaria de persistencia");
    }

    /**
     * Formatea un String JSON con pretty printing y lo escribe en disco.
     * Crea los directorios padres si no existen.
     */

   /* private*/public static String leerTextoConfiguracion(Path ruta) {  //se le pasa el path para facilitar el testeo
        try {
            return Files.readString(ruta, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el archivo de configuración en: " + ruta.toAbsolutePath(), e);
        }
    }

    //version sin parametros para obtenera ruta default de configuracion
    public static String leerTextoConfiguracion() {
        Path config = Path.of("configuracion.json");
        if (!Files.exists(config)) {
            config = Path.of("app/configuracion.json");
        }
        return leerTextoConfiguracion(config);
    }

    public static Configuracion obtenerConfiguracionInicial () {

        String parametrosJson = leerTextoConfiguracion(Path.of("configuracion.json"));

        JsonObject datosIniciales = JsonParser.parseString(parametrosJson).getAsJsonObject();

        Dificultad dificultadInicial = Dificultad.desdeNombre(datosIniciales.get("dificultad").getAsString());

        Long semillaInicial = (datosIniciales.has("semilla") && !datosIniciales.get("semilla").isJsonNull())
                ? datosIniciales.get("semilla").getAsLong() : null; //Por si se recibe una semilla null de base.

        return new Configuracion(dificultadInicial, semillaInicial); //Creo la dificultad inicial, para poder modificarla
    }

    public static void guardarJson(String json, int numeroDePlano) {
        if (json == null || numeroDePlano < 1) {
            throw new IllegalArgumentException("El contenido JSON y el número de plano debe ser positivo");
        }

        String nombreArchivo = String.format("salida-%02d.json", numeroDePlano); //%d -> entero decimal, 2 -> el número debe ocupar un
        Path destino = Path.of("salida", nombreArchivo);                   //ancho mínimo de 2 caracteres, 0 -> completa espacios vacios con 0

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

    public static void modificarJson(Configuracion configuracion) { //devuelve el json reescrito
        try {
            Path ruta = Path.of("configuracion.json");
            String parametrosJson = leerTextoConfiguracion(ruta);

            JsonObject jsonObject = JsonParser.parseString(parametrosJson).getAsJsonObject(); //parsear el texto a JsonObject

            jsonObject.addProperty("dificultad", configuracion.dificultad().nombreDificultad()); //cambia la dificultad

            if (configuracion.semilla() != null) { //cambia la semilla
                jsonObject.addProperty("semilla", configuracion.semilla());
            } else {
                jsonObject.add("semilla", com.google.gson.JsonNull.INSTANCE); //la semilla es null
            }

            String jsonFormateado = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(jsonObject); //formatea el json

            Files.writeString(ruta, jsonFormateado, StandardCharsets.UTF_8); //reescribe configuracion.json

//            return jsonFormateado; //devuelve el json para no tener que llamarlo nuevamente
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo actualizar el archivo de configuración.", e);
        }
    }

    public static String[] extraerPlano(String jsonSalida) {
        if (jsonSalida == null || jsonSalida.isBlank()) {
            throw new IllegalArgumentException("El JSON de salida no puede estar vacío");
        }

        JsonObject jsonObject = JsonParser.parseString(jsonSalida).getAsJsonObject();

        if (!jsonObject.has("plano") || jsonObject.get("plano").isJsonNull()) {
            throw new IllegalArgumentException("El JSON no contiene un campo 'plano' válido");
        }

        return new Gson().fromJson(jsonObject.get("plano"), String[].class);
    }

    public static String armarJsonEntradaPlano(Dificultad dificultad, long semilla, boolean salida, int cofres, int monstruos) {
        JsonObject json = new JsonObject();
        json.addProperty("version", 1);
        json.addProperty("dificultad", dificultad.nombreDificultad());
        json.addProperty("filas", dificultad.filas());
        json.addProperty("columnas", dificultad.columnas());
        json.addProperty("semilla", semilla);
        json.addProperty("cofres", cofres);
        json.addProperty("monstruos", monstruos);
        json.addProperty("conSalida", salida);

        return json.toString();
    }
}

