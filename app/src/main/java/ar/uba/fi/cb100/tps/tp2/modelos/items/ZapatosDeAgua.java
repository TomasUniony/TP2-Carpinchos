package ar.uba.fi.cb100.tps.tp2.modelos.items;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.AguaComoPiso;

/**
 * Zapatos de agua: al usarlos, el agua cuesta como piso durante DURACION_EN_PASOS pasos.
 * @param id identificador del tipo de item (por ejemplo "ZAPATOS_DE_AGUA")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class ZapatosDeAgua extends Item {

    private static final String ID = "ZAPATOS_DE_AGUA";
    public static final int DURACION_EN_PASOS = 20;

    /// Constructor
    public ZapatosDeAgua() {
        super(ID);
    }

    //El agua cuesta como piso durante DURACION_EN_PASOS pasos.
    @Override
    public void usar(Heroe heroe, Agenda agenda) {
        heroe.activarEfecto(new AguaComoPiso(), DURACION_EN_PASOS, agenda);
    }
}