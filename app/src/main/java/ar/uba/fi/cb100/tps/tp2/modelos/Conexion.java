package ar.uba.fi.cb100.tps.tp2.modelos;

import org.jgrapht.graph.DefaultWeightedEdge;

//extends DefaultWeightedEdge permite que JGraphT reconozca la clase como un tipo de arista para Graph.
public class Conexion extends DefaultWeightedEdge {

    private final Plano planoOrigen; //donde se encuentra la entrada de la conexion
    private final Posicion posOrigen; //donde esta ubicada la escalera o el pasillo
    private final Plano planoDestino; //el plano destino
    private final Posicion posDestino; //posicion de llegada en el plano destino
    private final Terreno tipo; //guarda el enum para identificar si es escalera o pasillo

    public Conexion(Plano planoOrigen, Posicion posOrigen, Plano planoDestino, Posicion posDestino, Terreno tipo) {

        if (tipo != Terreno.ESCALERA && tipo != Terreno.PASILLO) { //se verifica que el terreno sea pasillo o escalera
            throw new IllegalArgumentException(
                    "Una conexión solo puede ser de tipo ESCALERA o PASILLO. Tipo recibido: " + tipo
            );
        }

        this.planoOrigen = planoOrigen;
        this.posOrigen = posOrigen;
        this.planoDestino = planoDestino;
        this.posDestino = posDestino;
        this.tipo = tipo;
    }

    public Plano getPlanoOrigen() { return planoOrigen; }
    public Posicion getPosOrigen() { return posOrigen; }
    public Plano getPlanoDestino() { return planoDestino; }
    public Posicion getPosDestino() { return posDestino; }
    public Terreno getTipo() { return tipo; }

}
