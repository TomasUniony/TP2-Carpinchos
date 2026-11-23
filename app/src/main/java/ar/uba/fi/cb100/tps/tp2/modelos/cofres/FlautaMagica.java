package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;


/**
 * Cofre que deja la flauta en el inventario del heroe.
 */
public class FlautaMagica extends Cofre {

    /** Deja la flauta en el inventario del heroe. */
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.getInventario().agregar(Item.FLAUTA_MAGICA);
    }
}