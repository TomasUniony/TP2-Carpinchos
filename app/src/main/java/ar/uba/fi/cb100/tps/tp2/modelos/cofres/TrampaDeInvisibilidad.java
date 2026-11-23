package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;

public class TrampaDeInvisibilidad extends Cofre {

    private static final int PUNTOS = 0;
    private static final int DURACION_EN_PASOS = 15;

    /** Trampa: no da puntos. */
    public TrampaDeInvisibilidad() {
        super(PUNTOS);
    }

    /** Durante unos pasos el heroe no ve a los monstruos. */
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.activarEfecto(TipoDeEfecto.ENEMIGOS_OCULTOS, DURACION_EN_PASOS, agenda);
    }
}