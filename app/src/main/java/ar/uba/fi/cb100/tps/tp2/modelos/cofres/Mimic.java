package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;


/**
 * Cofre que deja la capa en el inventario del heroe.
 */
public class Mimic extends Cofre {

    private static final int PUNTOS = 0;
    private static final int DANIO= 50;

    /** Trampa: no da puntos. */
    public Mimic() {
        super(PUNTOS);
    }

    /** Le quita energia al heroe al abrirlo. */
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.quitarEnergia(DANIO);
    }
}