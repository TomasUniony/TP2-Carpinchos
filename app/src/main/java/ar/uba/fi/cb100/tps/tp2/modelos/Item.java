package ar.uba.fi.cb100.tps.tp2.modelos;

/**
 * Tipos de objeto que el heroe puede guardar en su inventario.
 * Solo estan los que define la catedra; el grupo agrega el resto.
 */
public enum Item {
    /** Al usarlo, durante 20 pasos el agua cuesta como piso. */
    ZAPATOS_DE_AGUA(20),
    /** Al usarlo, durante 20 pasos la lava cuesta como piso. */
    ZAPATOS_DE_LAVA(20),
    /** Al usarlo, durante 20 pasos el agua y la lava cuestan como piso. */
    ALAS(20),
    /** Al usarlo, durante 10 pasos se puede atravezar las paredes */
    CAPA_DE_INTANGIBILIDAD(10),
    /** Al usarlo, se gana una cant de energía determinada */
    POCION_DE_ENERGIA(0),
    /** Al usarlo, duerme a todos los enemigos del mapa durante 10 pasos*/
    FLAUTA_MAGICA(10),
    /** Cuando la energia llega a 0, la pluma se consume recuperando 50 de energía */
    PLUMA_DE_FENIX(0),
    /**Al usarla, te teletransporta a la entrada del piso*/
    POCION_DE_ESCAPE(0);

    private final int duracionEnPasos;

    Item(int duracionEnPasos) {
        this.duracionEnPasos = duracionEnPasos;
    }

    public int getDuracionEnPasos() {
        return this.duracionEnPasos;
    }
}