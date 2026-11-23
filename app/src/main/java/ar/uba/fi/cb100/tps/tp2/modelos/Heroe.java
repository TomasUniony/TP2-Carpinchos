package ar.uba.fi.cb100.tps.tp2.modelos;
public class Heroe {
    private int energia;
    private Posicion posicionActual;
    //private final Inventario(seria un arreglo de items, Item[]) inventario;
    private final Inventario inventario = new Inventario();
    private int puntos = 0;

    public Heroe(int fila, int columna, int energiaInicial) {
        if (fila <= 0) { //fila no puede ser menor ni igual a 0 (es una pared)
            throw new IllegalArgumentException("Numero de fila invalido: " + fila);
        }
        if (columna <= 0) { //columna no puede ser menor ni 0 (es una pared)
            throw new IllegalArgumentException("Numero de columna invalido: " + columna);
        }
        if (energiaInicial <= 0) {
            throw new IllegalArgumentException("Energia inicial invalida: " + energiaInicial);
        }
        this.posicionActual = new Posicion(fila, columna);
        this.energia = energiaInicial;
    }

    public int getEnergia() {
        return this.energia;
    }

    public Posicion getPosicion() {
        return this.posicionActual;
    }

    /** Inventario del heroe. */
    public Inventario getInventario() {
        return this.inventario;
    }

    
    public void sumarPuntos(int puntos){
        this.puntos += puntos;
    }

    public int getPuntos(){
        return this.puntos;
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
}
