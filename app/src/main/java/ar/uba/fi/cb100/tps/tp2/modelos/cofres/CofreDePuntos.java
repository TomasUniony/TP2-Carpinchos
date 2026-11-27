package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Cofre;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;

/**
 * Cofre de puntos: al abrirlo, el heroe recibe Energia.
 */
public class CofreDePuntos extends Cofre {

    //constructor
    public CofreDePuntos() {
        super();
    }

    // No hace nada mas, se suman en abrir.
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
    }
}