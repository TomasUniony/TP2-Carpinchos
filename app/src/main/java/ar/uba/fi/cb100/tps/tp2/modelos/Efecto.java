package ar.uba.fi.cb100.tps.tp2.modelos;

/**
 * Efecto temporal que puede estar activo sobre el heroe.
 * Cada tipo de efecto es una subclase que redefine solo lo que modifica:
 * el costo de un terreno, si se puede pisar, o si se ven los monstruos.
 * Por defecto un efecto no cambia nada.
 * @param id identificador del tipo de efecto (por ejemplo "AGUA_COMO_PISO")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
 */
public abstract class Efecto {

    private final String id;

    /**
     * @param id identificador del tipo de efecto (por ejemplo "AGUA_COMO_PISO")
     * @throws IllegalArgumentException si el id es nulo o esta vacio
     */
    protected Efecto(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id del efecto no puede ser nulo ni vacio");
        }
        this.id = id;
    }

    public final String id() {
        return this.id;
    }

    /**
     * Costo de energia de entrar a un terreno con este efecto activo.
     * Por defecto no lo modifica. Hay efectos que mosifican los costos de algunos terrenos (por ejemplo, el agua cuesta como piso).
     *
     * @param terreno     terreno al que entra el heroe
     * @param costoActual costo calculado hasta ahora (el del terreno u otro efecto)
     * @return costo que resulta de aplicar este efecto
     */
    public int costoDe(Terreno terreno, int costoActual) {
        return costoActual;
    }

    /**
     * Indica si este efecto permite pisar un terreno que normalmente no se puede pisar.
     * Por defecto no permite nada. Hay un efecto que permite atravesar paredes.
     *
     * @param terreno terreno al que quiere entrar el heroe
     * @return true si este efecto habilita pisarlo
     */
    public boolean permitePisar(Terreno terreno) {
        return false;
    }

    /**
     * Indica si este efecto oculta a los monstruos (la vista no los dibuja).
     * Por defecto no los oculta.
     */
    public boolean ocultaEnemigos() {
        return false;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Efecto)) {
            return false;
        }
        return this.id.equals(((Efecto) otro).id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return this.id;
    }
}