package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.validador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba un plano hecho a mano por cada regla del anexo B.4,
 * según lo pedido en el anexo B.7.
 * El plano BASE es válido: 9x11, 2 cofres, 2 monstruos y salida.
 */
class ValidadorDePlanoTest {

    private final ValidadorDePlano validador = new ValidadorDePlano();

    private static final String[] BASE = {
            "###########",
            "#E.C......#",
            "#.#.#.#.#.#",
            "#......M..#",
            "#.#.#.#.#.#",
            "#....M....#",
            "#.#.#.#.#.#",
            "#C.......S#",
            "###########"
    };

    // ---------- Métodos auxiliares ----------

    /**
     * Copia el plano y cambia el símbolo de una celda.
     */
    private static String[] con(String[] plano, int fila, int columna, char simbolo) {
        String[] copia = Arrays.copyOf(plano, plano.length);
        StringBuilder renglon = new StringBuilder(copia[fila]);
        renglon.setCharAt(columna, simbolo);
        copia[fila] = renglon.toString();
        return copia;
    }

    /**
     * Construye el JSON con los parámetros y el plano.
     */
    private static String json(int filas, int columnas, int cofres, int monstruos, boolean conSalida, String[] plano) {
        JsonObject parametros = new JsonObject();
        parametros.addProperty("filas", filas);
        parametros.addProperty("columnas", columnas);
        parametros.addProperty("cofres", cofres);
        parametros.addProperty("monstruos", monstruos);
        parametros.addProperty("conSalida", conSalida);
        JsonArray renglones = new JsonArray();
        for (String renglon : plano) {
            renglones.add(renglon);
        }
        JsonObject json = new JsonObject();
        json.add("parametros", parametros);
        json.add("entrada", posicionDe(plano, 'E'));
        json.add("salida", posicionDe(plano, 'S'));
        json.add("plano", renglones);
        return json.toString();
    }

    /**
     * Construye el JSON utilizando las dimensiones del plano.
     */
    private static String json(int cofres, int monstruos, String[] plano) {
        return json(plano.length, plano[0].length(), cofres, monstruos, true, plano);
    }

    /**
     * Busca la posición de un símbolo en el plano.
     */
    private static JsonElement posicionDe(String[] plano, char simbolo) {
        for (int i = 0; i < plano.length; i++) {
            int columna = plano[i].indexOf(simbolo);
            if (columna >= 0) {
                JsonObject posicion = new JsonObject();
                posicion.addProperty("fila", i + 1);
                posicion.addProperty("columna", columna + 1);
                return posicion;
            }
        }
        return JsonNull.INSTANCE;
    }

    /**
     * Comprueba que el validador rechace el plano indicando la regla esperada.
     */
    private void rechaza(String regla, String json) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> validador.validar(json));
        assertTrue(e.getMessage().startsWith(regla + ":"), "Se esperaba " + regla + " pero fue: " + e.getMessage());
    }

    // ---------- Plano válido y pruebas de las once reglas ----------

    @Test
    @DisplayName("el plano base cumple las once reglas")
    void planoValidoPasa() {
        assertDoesNotThrow(() -> validador.validar(json(2, 2, BASE)));
    }

    @Test
    @DisplayName("R1: la cantidad de filas no coincide con la informada")
    void r1Dimensiones() {
        rechaza("R1", json(9, 11, 2, 2, true, Arrays.copyOf(BASE, 8)));
    }

    @Test
    @DisplayName("R2: un símbolo que no está permitido")
    void r2Simbolos() {
        rechaza("R2", json(2, 2, con(BASE, 1, 5, 'X')));
    }

    @Test
    @DisplayName("R3: el borde tiene una celda que no es pared")
    void r3Borde() {
        rechaza("R3", json(2, 2, con(BASE, 0, 5, '.')));
    }

    @Test
    @DisplayName("R4: se pide salida pero el plano no tiene S")
    void r4EntradaYSalida() {
        rechaza("R4", json(2, 2, con(BASE, 7, 9, '.')));
    }

    @Test
    @DisplayName("R5: una celda transitable queda aislada de la entrada")
    void r5Conexo() {
        rechaza("R5", json(2, 2, con(con(BASE, 1, 8, '#'), 2, 9, '#')));
    }

    @Test
    @DisplayName("R6: un solo pasillo en serpiente, sin bifurcaciones")
    void r6Bifurcaciones() {
        String[] serpiente = {
                "###########",
                "#E........#",
                "#########.#",
                "#....C....#",
                "#.#########",
                "#....M....#",
                "#########.#",
                "#S........#",
                "###########"
        };
        rechaza("R6", json(1, 1, serpiente));
    }

    @Test
    @DisplayName("R7: menos del 30% de celdas transitables")
    void r7Proporcion() {
        String[] bloqueChico = {
                "###########",
                "#E.C..#####",
                "#....M#####",
                "#C.M..#####",
                "#....S#####",
                "###########",
                "###########",
                "###########",
                "###########"
        };
        rechaza("R7", json(2, 2, bloqueChico));
    }

    @Test
    @DisplayName("R8: falta un cofre respecto de los pedidos")
    void r8Cantidades() {
        rechaza("R8", json(2, 2, con(BASE, 1, 3, '.')));
    }

    @Test
    @DisplayName("R9: la salida no está a la distancia máxima de la entrada")
    void r9SalidaMasLejana() {
        rechaza("R9", json(2, 2, con(con(BASE, 7, 9, '.'), 7, 8, 'S')));
    }

    @Test
    @DisplayName("R10: el agua y la lava superan el 25% de las transitables")
    void r10Liquidos() {
        String[] inundado = Arrays.copyOf(BASE, BASE.length);
        inundado[1] = "#E.C....~~#";
        inundado[3] = "#~~~~~~M~~#";
        inundado[5] = "#~~~~M....#";
        rechaza("R10", json(2, 2, inundado));
    }

    @Test
    @DisplayName("R11: un monstruo a menos de 3 celdas de la entrada")
    void r11MonstruoCerca() {
        rechaza("R11", json(2, 2, con(con(BASE, 3, 7, '.'), 1, 2, 'M')));
    }
}
