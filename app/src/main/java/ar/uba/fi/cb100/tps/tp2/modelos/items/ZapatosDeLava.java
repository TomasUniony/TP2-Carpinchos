package ar.uba.fi.cb100.tps.tp2.modelos.items;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.LavaComoPiso;

/**
 * Zapatos de lava: al usarlos, la lava cuesta como piso durante DURACION_EN_PASOS pasos.
 * @param id identificador del tipo de item (por ejemplo "ZAPATOS_DE_LAVA")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class ZapatosDeLava extends Item {

    private static final String ID = "ZAPATOS_DE_LAVA";
    public static final int DURACION_EN_PASOS = 20;

    /// Constructor
    public ZapatosDeLava() {
        super(ID);
    }

    //La lava cuesta como piso durante DURACION_EN_PASOS pasos.
    @Override
    public void usar(Heroe heroe, Agenda agenda) {
        heroe.efectos().activar(new LavaComoPiso(), DURACION_EN_PASOS, agenda);
    }
}