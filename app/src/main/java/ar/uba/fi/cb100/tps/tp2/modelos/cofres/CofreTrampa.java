package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Cofre;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;

/**
 * Cofre trampa: al abrirlo, el heroe pierde energia.
 * @param danio energia que pierde el heroe (mayor a 0)
 * @throws IllegalArgumentException si el danio no es positivo
 */
public class CofreTrampa extends Cofre {

    private final int danio;

    /**
     * @param danio energia que pierde el heroe (mayor a 0)
     * @throws IllegalArgumentException si el danio no es positivo
     */
    public CofreTrampa(int danio) {
        super();
        if (danio <= 0) {
            throw new IllegalArgumentException("Danio invalido: " + danio);
        }
        this.danio = danio;
    }

    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.quitarEnergia(this.danio);
    }
}