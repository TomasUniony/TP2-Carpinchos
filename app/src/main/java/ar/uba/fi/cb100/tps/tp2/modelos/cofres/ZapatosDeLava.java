package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;


/**
 * Cofre que deja los zapatos de lava en el inventario del heroe.
 */
public class ZapatosDeLava extends Cofre {

    //Puntos por defecto
    /** Deja los zapatos en el inventario del heroe. */
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.getInventario().agregar(Item.ZAPATOS_DE_LAVA);
    }
}