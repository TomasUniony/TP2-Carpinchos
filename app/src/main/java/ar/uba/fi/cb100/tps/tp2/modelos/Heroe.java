package ar.uba.fi.cb100.tps.tp2.modelos;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Heroe {
    private int energia;
    private Posicion posicionActual;
    private final Inventario inventario = new Inventario();
    private int puntos = 0;
    //Efectos activos y el paso en el que vence cada uno.
    private final Map<Efecto, Long> efectosActivos = new HashMap<>();

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

    //JOSÉ: REVISAR ESTO---------------------

    /**
     * Activa un efecto durante una cantidad de pasos y programa en la agenda
     * el momento en que vence. Si ya estaba activo, queda el vencimiento mas lejano.
     */
    public void activarEfecto(Efecto efecto, int duracion, Agenda agenda) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("Duracion invalida: " + duracion);
        }
        long venceEn = agenda.getPasoActual() + duracion;
        this.efectosActivos.merge(efecto, venceEn, Long::max);
        agenda.programar(venceEn, () -> this.desactivarSiVenceEn(efecto, venceEn));
    }

    /** Indica si el efecto esta activo. */
    public boolean tieneEfecto(Efecto efecto) {
        return this.efectosActivos.containsKey(efecto);
    }

    /** Vista de solo lectura de los efectos activos (efecto -> paso de vencimiento). */
    public Map<Efecto, Long> getEfectosActivos() {
        return Collections.unmodifiableMap(this.efectosActivos);
    }

    /** Saca el efecto solo si su vencimiento guardado es justo este paso. */
    private void desactivarSiVenceEn(Efecto efecto, long paso) {
        this.efectosActivos.remove(efecto, paso);
    }
    
    //Indica si el heroe puede entrar a una celda de ese terreno.
    public boolean puedePisar(Terreno terreno) {
        return terreno.esTransitable() || this.tieneEfecto(Efecto.ATRAVESAR_PAREDES);
    }

    //Determina el costo dependiendo del efecto activo
    public int costoDe(Terreno terreno) {
        boolean costoComoPiso = switch (terreno) {
            case AGUA -> this.tieneEfecto(Efecto.AGUA_COMO_PISO);
            case LAVA -> this.tieneEfecto(Efecto.LAVA_COMO_PISO);
            case PARED -> true;
            default -> false;
        };
        return costoComoPiso ? Terreno.PISO.costoEnergia() : terreno.costoEnergia();
    }

}
