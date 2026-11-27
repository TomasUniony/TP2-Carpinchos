package ar.uba.fi.cb100.tps.tp2.modelos.efectos;

import ar.uba.fi.cb100.tps.tp2.modelos.Efecto;

/**
 * Efecto temporal que hace que los enemigos no se vean.
 * @param id identificador del tipo de efecto (por ejemplo "ENEMIGOS_OCULTOS")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public class EnemigosOcultos extends Efecto {

    private static final String ID = "ENEMIGOS_OCULTOS";

    public EnemigosOcultos() {
        super(ID);
    }

    //Vuelve los enemigos invisibles para el heroe.
    @Override
    public boolean ocultaEnemigos() {
        return true;
    }
}