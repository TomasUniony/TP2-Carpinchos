package ar.uba.fi.cb100.tps.tp2.modelos.items;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;
import ar.uba.fi.cb100.tps.tp2.modelos.Item;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.AtravesarParedes;

/**
 * Capa de intangibilidad: al usarla, el heroe atraviesa paredes durante DURACION_EN_PASOS pasos.
 * @param id identificador del tipo de item (por ejemplo "CAPA_DE_INTANGIBILIDAD")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class CapaDeIntangibilidad extends Item {

    private static final String ID = "CAPA_DE_INTANGIBILIDAD";
    public static final int DURACION_EN_PASOS = 10;

    //Constructor
    public CapaDeIntangibilidad() {
        super(ID);
    }

    //El heroe atraviesa paredes durante DURACION_EN_PASOS pasos.
    @Override
    public void usar(Heroe heroe, Agenda agenda) {
        heroe.activarEfecto(new AtravesarParedes(), DURACION_EN_PASOS, agenda);
    }
}