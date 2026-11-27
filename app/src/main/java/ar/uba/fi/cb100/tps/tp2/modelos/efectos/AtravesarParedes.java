package ar.uba.fi.cb100.tps.tp2.modelos.efectos;

import ar.uba.fi.cb100.tps.tp2.modelos.Efecto;
import ar.uba.fi.cb100.tps.tp2.modelos.Terreno;

/**
 * Efecto temporal que permite atravesar paredes.
 * @param id identificador del tipo de efecto (por ejemplo "ATRAVESAR_PAREDES")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class AtravesarParedes extends Efecto {

    private static final String ID = "ATRAVESAR_PAREDES";

    public AtravesarParedes() {
        super(ID);
    }

    // Este efecto permite atravesar paredes.
    @Override
    public boolean permitePisar(Terreno terreno) {
        return terreno == Terreno.PARED;
    }

    // Pisar una pared cuesta como pisar el piso.
    @Override
    public int costoDe(Terreno terreno, int costoActual) {
        return terreno == Terreno.PARED ? Terreno.PISO.costoEnergia() : costoActual;
    }
}