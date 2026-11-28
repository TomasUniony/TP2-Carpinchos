package ar.uba.fi.cb100.tps.tp2.modelos;

import ar.uba.fi.cb100.tps.tp2.modelos.mundo.Plano;
import ar.uba.fi.cb100.tps.tp2.modelos.mundo.Terreno;
import org.jgrapht.graph.DefaultWeightedEdge;

//extends DefaultWeightedEdge permite que JGraphT reconozca la clase como un tipo de arista para Graph.
public class Conexion extends DefaultWeightedEdge {

    private final Plano planoA; //donde se encuentra la entrada de la conexion
    private final Posicion posA; //donde esta ubicada la escalera o el pasillo
    private final Plano planoB; //el plano destino
    private final Posicion posB; //posicion de llegada en el plano destino
    private final Terreno tipo; //guarda el enum para identificar si es escalera o pasillo

    public Conexion(Plano planoA, Posicion posA, Plano planoB, Posicion posB, Terreno tipo) {

        if (tipo != Terreno.ESCALERA && tipo != Terreno.PASILLO) { //se verifica que el terreno sea pasillo o escalera
            throw new IllegalArgumentException(
                    "Una conexión solo puede ser de tipo ESCALERA o PASILLO. Tipo recibido: " + tipo
            );
        }

        this.planoA = planoA;
        this.posA = posA;
        this.planoB = planoB;
        this.posB = posB;
        this.tipo = tipo;
    }

    public Plano planoA() { return planoA; }
    public Posicion posA() { return posA; }
    public Plano planoB() { return planoB; }
    public Posicion posB() { return posB; }
    public Terreno tipo() { return tipo; }

    public boolean conectaEn(Plano plano, Posicion pos) { //se fija si la conexion esta ubicada en la celda y plano indicados
        return (this.planoA.equals(plano) && this.posA.equals(pos)) ||
                (this.planoB.equals(plano) && this.posB.equals(pos));
    }

    public Plano obtenerPlanoDestinoDesde(Plano planoActual) {
        if (planoActual.equals(this.planoA)) {
            return this.planoB;
        }
        if (planoActual.equals(this.planoB)) {
            return this.planoA;
        }
        return null;
    }
    public Posicion obtenerPosicionDestinoDesde(Plano planoActual) { //Devuelve la posicionDestino desde el plano actual
        if (planoActual.equals(this.planoA)) {
            return this.posB;
        }
        if (planoActual.equals(this.planoB)) {
            return this.posA;
        }
        return null; // El plano indicado no pertenece a esta conexión
    }

}
