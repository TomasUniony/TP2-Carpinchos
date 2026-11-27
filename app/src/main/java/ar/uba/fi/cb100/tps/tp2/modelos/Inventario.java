package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Inventario del heroe: para cada tipo de item guarda cuantas unidades tiene.
 * Usa una tabla hash (tipo -> cantidad).
 * @param items tabla hash de tipo -> cantidad
 * @throws IllegalArgumentException si el tipo es nulo o la cantidad no es positiva
 */
public class Inventario {

    private final Map<Item, Integer> items = new HashMap<>();

    /**
     * Agrega una unidad del item.
     *
     * @param tipo tipo de item a agregar
     * @throws IllegalArgumentException si el tipo es nulo
     */
    public void agregar(Item tipo) {
        this.agregar(tipo, 1);
    }

    /**
     * Agrega varias unidades del item.
     *
     * @param tipo     tipo de item a agregar
     * @param cantidad unidades a agregar (mayor a 0)
     * @throws IllegalArgumentException si el tipo es nulo o la cantidad no es positiva
     */
    public void agregar(Item tipo, int cantidad) {
        validarTipo(tipo);
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad invalida: " + cantidad);
        }
        this.items.merge(tipo, cantidad, Integer::sum);
    }

    /**
     * Quita una unidad del item. Si era la ultima, el item desaparece del inventario.
     *
     * @param tipo tipo de item a quitar
     * @return true si habia una unidad y se quito, false si no tenia ninguna
     */
    public boolean quitar(Item tipo) {
        Integer actual = this.items.get(tipo);
        if (actual == null) {
            return false;
        }
        if (actual == 1) {
            this.items.remove(tipo);
        } else {
            this.items.put(tipo, actual - 1);
        }
        return true;
    }

    public int cantidad(Item tipo) {
        return this.items.getOrDefault(tipo, 0);
    }

    public boolean contiene(Item tipo) {
        return this.items.containsKey(tipo);
    }

    public boolean estaVacio() {
        return this.items.isEmpty();
    }

    //Vista de solo lectura del contenido, util para mostrarlo o guardarlo en JSON.
    public Map<Item, Integer> comoMapa() {
        return Collections.unmodifiableMap(this.items);
    }

    private static void validarTipo(Item tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de item no puede ser nulo");
        }
    }
}