package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;

public class CofreDePuntos extends Cofre{

    //Puntos por defecto
    //No tiene efecto propio: los puntos los suma abrir().
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        // Sin efecto adicional.
    }
}