package ar.uba.fi.cb100.tps.tp2.modelos.efectos;

import ar.uba.fi.cb100.tps.tp2.modelos.Efecto;
import ar.uba.fi.cb100.tps.tp2.modelos.Terreno;

/**
 * Efecto temporal que hace que la lava cueste como piso.
 * @param id identificador del tipo de efecto (por ejemplo "LAVA_COMO_PISO")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class LavaComoPiso extends Efecto {

    private static final String ID = "LAVA_COMO_PISO";

    public LavaComoPiso() {
        super(ID);
    }

    //Entrar a la lava cuesta como entrar al piso.
    @Override
    public int costoDe(Terreno terreno, int costoActual) {
        return terreno == Terreno.LAVA ? Terreno.PISO.costoEnergia() : costoActual;
    }
}