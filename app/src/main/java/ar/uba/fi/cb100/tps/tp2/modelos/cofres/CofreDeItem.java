package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Cofre;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;

/**
 * Cofre de item: al abrirlo, el heroe recibe un item.
 * @param item item que recibe el heroe
 * @throws IllegalArgumentException si el item es nulo
 */
public class CofreDeItem extends Cofre {

    private final Item item;

    /**
     * @param item item que recibe el heroe
     * @throws IllegalArgumentException si el item es nulo
     */
    public CofreDeItem(Item item) {
        super();
        if (item == null) {
            throw new IllegalArgumentException("El item no puede ser nulo");
        }
        this.item = item;
    }

    // El efecto es sumar el item al inventario del héroe.
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.getInventario().agregar(this.item);
    }
}