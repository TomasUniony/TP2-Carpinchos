package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.validador;

import ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.utilidades.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.enumeraciones.Direccion;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * Valida los planos de laberintos del anexo B.4 del TP2
 */
public class ValidadorDePlano {
    private record Celda(int fila, int columna) {
    }

    private record Conteos(int transitables, int bifurcaciones, int cantidadAguaLava, int cofresEncontrados,
            int monstruosEncontrados, int entradasEncontradas,
            int salidasEncontradas, Celda entrada, Celda salida) {
    }

    /**
     * Valida que un plano cumpla las 11 reglas de validación
     * 
     * @param planoJson plano y parámetros en formato JSON
     */
    public void validar(String planoJson) {
        ValidacionesUtiles.validarDistintoDeNull(planoJson, "planoJson");
        JsonObject json = JsonParser.parseString(planoJson).getAsJsonObject();
        JsonObject parametros = json.getAsJsonObject("parametros");
        int filas = parametros.get("filas").getAsInt();
        int columnas = parametros.get("columnas").getAsInt();
        int cofres = parametros.get("cofres").getAsInt();
        int monstruos = parametros.get("monstruos").getAsInt();
        boolean conSalida = parametros.get("conSalida").getAsBoolean();
        JsonArray plano = json.getAsJsonArray("plano");
        char[][] grilla = leerPlano(plano, filas, columnas);
        Conteos conteos = analizarPlano(grilla);
        Celda entrada = conteos.entrada();
        Celda salida = conteos.salida();
        validarEntradaYSalida(json, conSalida, conteos);
        int[][] distancias = calcularDistancias(grilla, entrada);
        int celdasAlcanzadas = 0, distanciaMaxima = 0;
        for (int[] filaDistancias : distancias) {
            for (int distancia : filaDistancias) {
                if (distancia >= 0) {
                    celdasAlcanzadas++;
                    distanciaMaxima = Math.max(distanciaMaxima, distancia);
                }
            }

        }

        ValidacionesUtiles.validarFalso(celdasAlcanzadas != conteos.transitables(), "R5: plano desconectado");
        ValidacionesUtiles.validarFalso(
                conteos.transitables() == 0 || conteos.bifurcaciones() * 100 < conteos.transitables() * 10,
                "R6: pocas bifurcaciones");
        ValidacionesUtiles.validarFalso(
                conteos.transitables() * 100 < filas * columnas * 30
                        || conteos.transitables() * 100 > filas * columnas * 60,
                "R7: proporción de transitables incorrecta");
        ValidacionesUtiles.validarFalso(
                conteos.cofresEncontrados() != cofres || conteos.monstruosEncontrados() != monstruos,
                "R8: cantidades incorrectas");
        ValidacionesUtiles.validarFalso(conSalida && distancias[salida.fila()][salida.columna()] != distanciaMaxima,
                "R9: la salida no es la más lejana");
        ValidacionesUtiles.validarFalso(conteos.cantidadAguaLava() * 100 > conteos.transitables() * 25,
                "R10: exceso de agua y lava");
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                if (grilla[fila][columna] == 'M') {
                    ValidacionesUtiles.validarFalso(
                            Math.abs(fila - entrada.fila()) + Math.abs(columna - entrada.columna()) < 3,
                            "R11: monstruo demasiado cerca de entrada");
                }
            }
        }
    }

    /**
     * Construye la matriz del plano y verifica sus dimensiones
     *
     * @param plano    filas del laberinto en formato JSON
     * @param filas    cantidad de filas recibida
     * @param columnas cantidad de columnas recibida
     * @return matriz de caracteres del plano
     */
    private static char[][] leerPlano(JsonArray plano, int filas, int columnas) {
        ValidacionesUtiles.validarFalso(filas < 5 || columnas < 5 || plano.size() != filas,
                "R1: dimensiones incorrectas");
        char[][] grilla = new char[filas][];
        for (int fila = 0; fila < filas; fila++) {
            grilla[fila] = plano.get(fila).getAsString().toCharArray();
            ValidacionesUtiles.validarFalso(grilla[fila].length != columnas, "R1: longitud de un renglón incorrecta");

        }
        return grilla;
    }

    /**
     * Recorre el plano, valida sus símbolos y bordes, y cuenta los elementos
     * necesarios
     * para comprobar las reglas.
     * 
     * @param grilla matriz de caracteres del plano
     * @return conteos y posiciones de entrada y salida
     */
    private static Conteos analizarPlano(char[][] grilla) {
        int transitables = 0, bifurcaciones = 0, cantidadAguaLava = 0, cofresEncontrados = 0;
        int monstruosEncontrados = 0, entradasEncontradas = 0, salidasEncontradas = 0;
        Celda entrada = null, salida = null;

        int filas = grilla.length;
        int columnas = grilla[0].length;

        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                char simbolo = grilla[fila][columna];
                ValidacionesUtiles.validarFalso("#.~^CMES".indexOf(simbolo) == -1, "R2: símbolo inválido");
                boolean esBorde = fila == 0 || columna == 0 || fila == filas - 1 || columna == columnas - 1;
                ValidacionesUtiles.validarFalso(esBorde && simbolo != '#', "R3: borde sin pared");
                if (simbolo != '#') {
                    transitables++;

                    if (vecinos(grilla, fila, columna) >= 3) {
                        bifurcaciones++;
                    }

                    if (simbolo == '~' || simbolo == '^') {
                        cantidadAguaLava++;
                    }

                    if (simbolo == 'C') {
                        cofresEncontrados++;
                    }

                    if (simbolo == 'M') {
                        monstruosEncontrados++;
                    }

                    if (simbolo == 'E') {
                        entradasEncontradas++;
                        entrada = new Celda(fila, columna);
                    }

                    if (simbolo == 'S') {
                        salidasEncontradas++;
                        salida = new Celda(fila, columna);
                    }
                }
            }

        }

        return new Conteos(
                transitables, bifurcaciones, cantidadAguaLava,
                cofresEncontrados, monstruosEncontrados,
                entradasEncontradas, salidasEncontradas,
                entrada, salida);

    }

    /**
     * Valida la cantidad de entradas y salidas y comprueba
     * que las posiciones coincidan con las declaradas en el JSON
     * 
     * @param json      datos del plano
     * @param conSalida indica si el plano debe tener salida
     * @param conteos   resultados del análisis del plano
     */

    private static void validarEntradaYSalida(JsonObject json, boolean conSalida, Conteos conteos) {
        boolean cantidadSalidasIncorrectas;
        if (conSalida) {
            cantidadSalidasIncorrectas = conteos.salidasEncontradas() != 1;
        } else {
            cantidadSalidasIncorrectas = conteos.salidasEncontradas() != 0;
        }

        ValidacionesUtiles.validarFalso(conteos.entradasEncontradas() != 1 || cantidadSalidasIncorrectas,
                "R4: cantidad de entradas o salidas incorrectas");
        boolean salidaDeclaradaCorrecta;
        if (conSalida) {
            salidaDeclaradaCorrecta = coincide(json.get("salida"), conteos.salida());
        } else {
            salidaDeclaradaCorrecta = esNulo(json.get("salida"));
        }

        ValidacionesUtiles.validarFalso(!coincide(json.get("entrada"), conteos.entrada()) || !salidaDeclaradaCorrecta,
                "R4: posiciones declaradas incorrectas");

    }

    /**
     * Calcula mediante BFS las distancias desde la entrada
     * hasta las celdas transitables del plano
     * 
     * @param grilla  matriz de caracteres del plano
     * @param entrada posición inicial del recorrido
     * @return matriz de distancias, con -1 para celdas no alcanzadas
     */
    private static int[][] calcularDistancias(char[][] grilla, Celda entrada) {
        int filas = grilla.length, columnas = grilla[0].length;
        int[][] distancias = new int[filas][columnas];
        for (int[] filaDistancias : distancias) {
            Arrays.fill(filaDistancias, -1);
        }
        ArrayDeque<Celda> cola = new ArrayDeque<>();
        cola.addLast(entrada);
        distancias[entrada.fila()][entrada.columna()] = 0;
        while (!cola.isEmpty()) {
            Celda actual = cola.removeFirst();
            int distancia = distancias[actual.fila()][actual.columna()];
            for (Direccion direccion : Direccion.values()) {
                int fila = actual.fila() + direccion.getDesplazamientoFila();
                int columna = actual.columna() + direccion.getDesplazamientoColumna();

                if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas && grilla[fila][columna] != '#'
                        && distancias[fila][columna] == -1) {
                    distancias[fila][columna] = distancia + 1;
                    cola.addLast(new Celda(fila, columna));
                }
            }
        }
        return distancias;
    }

    /**
     * Cuenta las celdas adyacentes a una posición
     * 
     * @param grilla  matriz de caracteres del plano
     * @param fila    fila de la celda
     * @param columna columna de la celda
     * @return cantidad de vecinos transitables
     */
    private static int vecinos(char[][] grilla, int fila, int columna) {
        int cantidad = 0;
        for (Direccion direccion : Direccion.values()) {
            int filaVecina = fila + direccion.getDesplazamientoFila();
            int columnaVecina = columna + direccion.getDesplazamientoColumna();
            if (filaVecina >= 0 && filaVecina < grilla.length && columnaVecina >= 0 && columnaVecina < grilla[0].length
                    && grilla[filaVecina][columnaVecina] != '#') {
                cantidad++;
            }
        }
        return cantidad;
    }

    /**
     * Comprueba si un elemento JSON representa un valor nulo
     * 
     * @param elemento elemento JSON a comprobar
     * @return true si el elemento es JsonNull
     */
    private static boolean esNulo(JsonElement elemento) {
        return elemento != null && elemento.isJsonNull();
    }

    /**
     * Comprueba si una posición JSON coincide con una celda del plano
     * Convierte los índices de la matriz a coordenadas que comienzan en uno
     * 
     * @param posicion posicion declarada en el JSON
     * @param celda    posicion de la matriz
     * @return true si ambas posiciones coinciden
     */
    private static boolean coincide(JsonElement posicion, Celda celda) {
        if (posicion == null || posicion.isJsonNull()) {
            return false;
        }
        JsonObject coordenadas = posicion.getAsJsonObject();
        return coordenadas.get("fila").getAsInt() == celda.fila() + 1 &&
                coordenadas.get("columna").getAsInt() == celda.columna() + 1;
    }

}
