package ar.uba.fi.cb100.tps.tp2.modelos.items;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;

/**
 * Pocion de energia: al usarla, el heroe gana ENERGIA_QUE_DA de energia.
 * @param id identificador del tipo de item (por ejemplo "POCION_DE_ENERGIA")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class PocionDeEnergia extends Item {

    private static final String ID = "POCION_DE_ENERGIA";
    public static final int ENERGIA = 25;

    /** Crea una pocion de energia. */
    public PocionDeEnergia() {
        super(ID);
    }

    // Al usarla, el heroe gana ENERGIA de energia.
    @Override
    public void usar(Heroe heroe, Agenda agenda) {
        heroe.agregarEnergia(ENERGIA);
    }
}