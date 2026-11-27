package ar.uba.fi.cb100.tps.tp2.modelos.efectos;

import ar.uba.fi.cb100.tps.tp2.modelos.Efecto;
import ar.uba.fi.cb100.tps.tp2.modelos.Terreno;

/**
 * Efecto temporal que hace que el agua cueste como piso.
 * @param id identificador del tipo de efecto (por ejemplo "AGUA_COMO_PISO")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class AguaComoPiso extends Efecto {

    private static final String ID = "AGUA_COMO_PISO";

    public AguaComoPiso() {
        super(ID);
    }

    // Entrar al agua cuesta como entrar al piso.
    @Override
    public int costoDe(Terreno terreno, int costoActual) {
        return terreno == Terreno.AGUA ? Terreno.PISO.costoEnergia() : costoActual;
    }
}