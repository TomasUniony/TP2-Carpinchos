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

        if (!this.planoActual.esTransitable(posicionDestino)) { //si no es transitable volver
            return;
        }

        Terreno terreno = this.planoActual.terreno(posicionDestino); //se obtiene el terreno
        heroe.movimiento(direccion); //se mueve al heroe
        this.heroe.quitarEnergia(terreno.costoEnergia()); //se quita la energia correspondiente al terreno

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
