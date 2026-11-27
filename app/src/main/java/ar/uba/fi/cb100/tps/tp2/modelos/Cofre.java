package ar.uba.fi.cb100.tps.tp2.modelos;

public abstract class Cofre{

    //dejo un ppor defecto por sí en algún momento quiero agregar un cofre que de más o menos puntos
    public static final int PUNTOS_POR_DEFECTO = 100;

    private final int puntos;

    //Cofre que suma los puntos por defecto
    protected Cofre() {
        this.puntos= PUNTOS_POR_DEFECTO;
    }

    /** Cofre que suma una cantidad propia de puntos.
     * @param puntos cantidad de puntos que suma el cofre
     * @throws IllegalArgumentException si los puntos son negativos
     */
    protected Cofre(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("Puntos invalidos: " + puntos);
        }
        this.puntos = puntos;
    }

    public final void abrir(Heroe heroe, Agenda agenda) {
        heroe.sumarPuntos(this.puntos);
        this.aplicarEfecto(heroe, agenda);
    }

    protected abstract void aplicarEfecto(Heroe heroe, Agenda agenda);
}