package ar.uba.fi.cb100.tps.tp2.modelos;

import ar.uba.fi.cb100.tps.tp2.GeneradorDeLaberinto;

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

        Posicion posicionDestino = this.heroe.posicion().desplazar(direccion);  //se obtiene la posicion destino (no se mueve el heroe)

        //si la posicion destino no esta dentro del plano actual, no se realiza el movimiento
        if (!this.planoActual.contiene(posicionDestino)) {
            return;
        }
        
        Terreno terreno = this.planoActual.terreno(posicionDestino); //se obtiene el terreno
        
        boolean puedePisar = terreno.esTransitable() || (this.heroe.efectos().permitePisar(terreno) 
                                && !this.planoActual.esBorde(posicionDestino));
        if (!puedePisar) {
            return;
        }

        int costo = this.heroe.efectos().costoDe(terreno);
        this.heroe.movimiento(direccion);
        this.heroe.quitarEnergia(costo);

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
