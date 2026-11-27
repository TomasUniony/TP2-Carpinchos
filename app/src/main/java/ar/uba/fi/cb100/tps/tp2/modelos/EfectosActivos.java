package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Mantiene los efectos activos de un heroe y el paso en que vence cada uno.
 * Permite consultar si un efecto esta activo, el costo de entrar a un terreno
 * y si algun efecto activo permite pisar un terreno que normalmente no se puede pisar.
 * Por ejemplo, la pared normalmente no se puede pisar, pero si el heroe tiene el efecto de atravesar paredes, entonces si se puede pisar.
 * Por defecto no hay ningun efecto activo y no se puede pisar la pared.
 * @param efecto efecto activo (por ejemplo, "AGUA_COMO_PISO")
 * @param paso paso en que vence el efecto
 * @throws IllegalArgumentException si el efecto es nulo o el paso no es positivo
 */
public class EfectosActivos {

    private final Map<Efecto, Long> venceEn = new HashMap<>();

    /**
     * Activa un efecto durante una cantidad de pasos y programa en la agenda
     * el momento en que vence. Si ya estaba activo, queda el vencimiento mas lejano.
     *
     * @param efecto   efecto a activar
     * @param duracion cantidad de pasos que dura (mayor a 0)
     * @param agenda   agenda donde se programa el vencimiento
     * @throws IllegalArgumentException si el efecto o la agenda son nulos, o la duracion no es positiva
     */
    public void activar(Efecto efecto, int duracion, Agenda agenda) {
        if (efecto == null) {
            throw new IllegalArgumentException("El efecto no puede ser nulo");
        }
        if (agenda == null) {
            throw new IllegalArgumentException("La agenda no puede ser nula");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("Duracion invalida: " + duracion);
        }
        long vencimiento = agenda.getPasoActual() + duracion;
        this.venceEn.merge(efecto, vencimiento, Long::max);
        //Programa un evento en la agenda para desactivar el efecto cuando venza. 
        agenda.programar(vencimiento, () -> this.desactivarSiVenceEn(efecto, vencimiento));
    }

    /**
     * Indica si el efecto esta activo.
     *
     * @param efecto efecto a consultar
     * @return true si el efecto esta activo, false en caso contrario
     */
    public boolean existe(Efecto efecto) {
        return this.venceEn.containsKey(efecto);
    }

    
    public boolean estaVacio() {
        return this.venceEn.isEmpty();
    }

    /**
     * Indica si algun efecto activo habilita pisar ese terreno, aunque
     * normalmente no se pueda (por ejemplo, la pared).
     *
     * @param terreno terreno al que quiere entrar el heroe
     */
    public boolean permitePisar(Terreno terreno) {
        for (Efecto efecto : this.venceEn.keySet()) {
            if (efecto.permitePisar(terreno)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Costo de energia de entrar a un terreno con los efectos activos.
     * Por defecto no lo modifica. Hay efectos que mosifican los costos de algunos terrenos (por ejemplo, el agua cuesta como piso).
     * @param terreno terreno al que entra el heroe
     * @return costo que resulta de aplicar los efectos activos
     */
    public int costoDe(Terreno terreno) {
        int costo = terreno.costoEnergia();
        for (Efecto efecto : this.venceEn.keySet()) {
            costo = efecto.costoDe(terreno, costo);
        }
        return costo;
    }

    /**
     * Indica si algun efecto activo oculta a los monstruos.
     *
     * @return true si algun efecto activo oculta a los monstruos, false en caso contrario
     */
    public boolean ocultaEnemigos() {
        for (Efecto efecto : this.venceEn.keySet()) {
            if (efecto.ocultaEnemigos()) {
                return true;
            }
        }
        return false;
    }

    //Vista de solo lectura de los efectos activos (efecto -> paso de vencimiento).
    public Map<Efecto, Long> comoMapa() {
        return Collections.unmodifiableMap(this.venceEn);
    }

    /**
     * Saca el efecto solo si su vencimiento guardado es justo este paso.
     * Si se reactivo con un vencimiento mas lejano, el evento viejo no hace nada.
     * @param efecto efecto a desactivar
     * @param paso paso en que se vencio el efecto
     * @throws IllegalArgumentException si el efecto es nulo o el paso no es positivo
     */
    private void desactivarSiVenceEn(Efecto efecto, long paso) {
        this.venceEn.remove(efecto, paso);
    }
}