package ar.uba.fi.cb100.tps.tp2.modelos;
public class Heroe {
    private int energia;
    private Posicion posicionActual;
    //private final Inventario(seria un arreglo de items, Item[]) inventario;

    public Heroe(Posicion pos, int energiaInicial) {
        if (energiaInicial <= 0) {
            throw new IllegalArgumentException("Energia inicial invalida: " + energiaInicial);
        }
        this.posicionActual = pos;
        this.energia = energiaInicial;
    }

    public int energia() {
        return this.energia;
    }

    public Posicion posicion() {
        return this.posicionActual;
    }

    public void agregarEnergia(int energia){
        if (energia <= 0) {
            throw new IllegalArgumentException("Energia a agregar invalida: " + energia);
        }
        this.energia += energia;
    }

    public void quitarEnergia(int energia) {
        if (energia <= 0) {
            throw new IllegalArgumentException("Energia a quitar invalida: " + energia);
        }
        this.energia = Math.max(0, this.energia - energia);
    }

    public boolean tieneEnergia() {
        return this.energia != 0;
    }

    public void movimiento(Direccion dir) {
        this.posicionActual = this.posicionActual.desplazar(dir);
    }

    public void cambiarDePlano(Posicion posicion){
        this.posicionActual = posicion;
    }
}
