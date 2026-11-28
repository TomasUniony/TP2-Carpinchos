package ar.uba.fi.cb100.tps.tp2.modelos.mundo;

import ar.uba.fi.cb100.tps.tp2.GeneradorDeLaberinto;
import ar.uba.fi.cb100.tps.tp2.modelos.*;

public class Partida {
    private final Mazmorra mazmorra;
    private final Heroe heroe;
    private Plano planoActual;
    private boolean victoria;

    public Partida (GeneradorDeLaberinto generadorDeLaberinto, Configuracion configuracion) {
        this.mazmorra = new Mazmorra(generadorDeLaberinto, configuracion); //genera la mazmorra
        this.planoActual = this.mazmorra.planoInicial(); //se trae el plano inicial
        this.heroe = new Heroe(this.planoActual.posicionHeroe(), 100);
        this.victoria = false;
    }

    public boolean finalizada() {
        return this.victoria || !this.heroe.tieneEnergia();
    };

    public void ejecutarTurno(String entrada) {
        if(entrada.length() != 1) { //solo se acepta un caracter de entrada
            return;
        }

        Direccion direccion = Direccion.teclaIngresada(entrada.charAt(0));

        if (direccion == null) { //si el caracter ingresado es distinto al solicitado,
            return;              //direccion devuelve null y no se realiza el movimiento
        }

        Posicion posicionActual = this.heroe.posicion();
        Posicion posicionDestino = this.heroe.posicion().desplazar(direccion);  //se obtiene la posicion destino (no se mueve el heroe)

        Celda celdaActual = this.planoActual.celda(posicionActual); //se obtiene la celda actual
        Celda celdaDestino = this.planoActual.celda(posicionDestino); //se obtiene la celda destino
        Terreno terreno = celdaDestino.terreno();


        boolean puedePisar = terreno.esTransitable() || (this.heroe.efectos().permitePisar(terreno)
                && !this.planoActual.esBorde(posicionDestino));

        if (puedePisar) { //si no es transitable volver
            return;
        } else if (!celdaDestino.estaVacia()) { //CAMBIAR, LO QUE PASARIA SI CONTIENE UNA ENTIDAD
            celdaDestino.vaciarContenido();
        }

        celdaActual.vaciarContenido(); //se saca al heroe de la celda

        heroe.movimiento(direccion); //se mueve al heroe
        celdaDestino.cargarContenido(Entidad.HEROE);

        int costo = this.heroe.efectos().costoDe(terreno);
        this.heroe.quitarEnergia(costo); //se quita la energia



        if (terreno == Terreno.ESCALERA || terreno == Terreno.PASILLO) {
            Conexion conexion = this.mazmorra.buscarConexion(this.planoActual, posicionDestino);
            if (conexion != null) {
                this.planoActual = conexion.obtenerPlanoDestinoDesde(this.planoActual);
                this.heroe.cambiarDePlano(conexion.obtenerPosicionDestinoDesde(this.planoActual));
            }
        }

        if (terreno == Terreno.SALIDA) {
            this.victoria = true;
        }
    }

    public Plano planoActual() {
        return this.planoActual;
    }

    public Heroe heroe() {
        return this.heroe;
    }

    public boolean victoria() {
        return this.victoria;
    }

}
