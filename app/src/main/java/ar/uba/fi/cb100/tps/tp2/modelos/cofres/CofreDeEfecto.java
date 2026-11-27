package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import ar.uba.fi.cb100.tps.tp2.modelos.Agenda;
import ar.uba.fi.cb100.tps.tp2.modelos.Cofre;
import ar.uba.fi.cb100.tps.tp2.modelos.Efecto;
import ar.uba.fi.cb100.tps.tp2.modelos.Heroe;

/** Cofre que activa un efecto temporal sobre el heroe apenas se abre.
 * @param efecto efecto a activar
 * @param duracion cantidad de pasos que dura (mayor a 0)
 * @throws IllegalArgumentException si el efecto es nulo o la duracion no es positiva
 */
public class CofreDeEfecto extends Cofre {

    private final Efecto efecto;
    private final int duracion;

    /**
     * @param efecto   efecto a activar
     * @param duracion cantidad de pasos que dura (mayor a 0)
     * @throws IllegalArgumentException si el efecto es nulo o la duracion no es positiva
     */
    public CofreDeEfecto(Efecto efecto, int duracion) {
        super();
        if (efecto == null) {
            throw new IllegalArgumentException("El efecto no puede ser nulo");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("Duracion invalida: " + duracion);
        }
        this.efecto = efecto;
        this.duracion = duracion;
    }

    // El efecto queda activo y la agenda programa su vencimiento.
    @Override
    protected void aplicarEfecto(Heroe heroe, Agenda agenda) {
        heroe.efectos().activar(this.efecto, this.duracion, agenda);
    }
}