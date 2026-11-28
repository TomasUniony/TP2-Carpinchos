package ar.uba.fi.cb100.tps.tp2.modelos.mundo;

public class Celda {
    private Terreno terreno; // PISO, PARED, ESCALERA, PASILLO
    private Entidad contenido;      // Cofre, Monstruo, Jugador (puede ser null o un Optional)

    public Celda(Terreno terreno) {
        this.terreno = terreno;
        this.contenido = null;
    }

    public boolean estaVacia() {
        return contenido == null;
    }

    public boolean esTransitable() {
        return terreno.esTransitable() && (estaVacia() || contenido.permitePaso());
    }

    public void cargarContenido(Entidad entidad) {
        if (!estaVacia()) {
            throw new IllegalArgumentException("No se puede cargar la celda, esta ocupada por + " + contenido.simbolo());
        } else if (entidad == null) {
            throw new IllegalArgumentException("No se puede cargar una entidad nula");
        } else {
            this.contenido = entidad;
        }
    }

    public void vaciarContenido() {
        this.contenido = null;
    }

    public Terreno terreno() {
        return this.terreno;
    }

    public Entidad contenido() {
        return this.contenido;
    }

    public void cambiarTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public char simbolo() {
        if (contenido != null) {
            return contenido.simbolo();
        }
        return terreno.simbolo();
    }
}
