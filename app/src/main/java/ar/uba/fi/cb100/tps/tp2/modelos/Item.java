package ar.uba.fi.cb100.tps.tp2.modelos;

/** 
 * Representa un item que puede ser usado por el heroe.
 * @param id identificador del tipo de item (por ejemplo "ZAPATOS_DE_AGUA")
 * @throws IllegalArgumentException si el id es nulo o esta vacio
*/
public abstract class Item {

    private final String id;

    /**
     * @param id identificador del tipo de item (por ejemplo "ZAPATOS_DE_AGUA")
     * @throws IllegalArgumentException si el id es nulo o esta vacio
     */
    protected Item(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id del item no puede ser nulo ni vacio");
        }
        this.id = id;
    }

    /// Getters
    public final String getId() {
        return this.id;
    }

    /**
     * Efecto del item al usarlo. El inventario ya lo descuenta antes
     * (ver Heroe.usarItem), aca solo se aplica el efecto.
     * El efecto es responsabilidad de la subclase, que puede ser instantaneo o programar efectos que vencen en el futuro.
     *
     * @param heroe  heroe que usa el item
     * @param agenda agenda donde se programan los efectos que vencen
     */
    public abstract void usar(Heroe heroe, Agenda agenda);

    
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Item)) {
            return false;
        }
        return this.id.equals(((Item) otro).id);
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