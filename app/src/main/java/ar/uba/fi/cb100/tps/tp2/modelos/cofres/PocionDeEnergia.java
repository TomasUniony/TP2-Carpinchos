package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;


/**
 * Cofre que deja la pocion en el inventario del heroe.
 */
public class PocionDeEnergia extends Cofre {

    private static final int CANTIDAD_POR_DEFECTO = 1;

    private final int cantidad;

    /** Cofre con la cantidad de pociones por defecto. */
    public PocionDeEnergia() {
        this.cantidad = CANTIDAD_POR_DEFECTO;
    }

    /** Cofre que da una cantidad propia de pociones. */
    public PocionDeEnergia(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad de pociones invalida: " + cantidad);
        }
        this.cantidad = cantidad;
    }

    /** Deja la pocion en el inventario del heroe. */
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.getInventario().agregar(Item.POCION_DE_ENERGIA, this.cantidad);
    }
}