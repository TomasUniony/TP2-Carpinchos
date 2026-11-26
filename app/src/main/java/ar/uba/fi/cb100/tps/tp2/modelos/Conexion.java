package ar.uba.fi.cb100.tps.tp2.modelos;

import org.jgrapht.graph.DefaultWeightedEdge;

//extends DefaultWeightedEdge permite que JGraphT reconozca la clase como un tipo de arista para Graph.
public class Conexion extends DefaultWeightedEdge {

    public enum TipoConexion { ESCALERA, PASILLO } //para poder identificar si es escalera o pasillo

    private final Plano planoOrigen; //donde se encuentra la entrada de la conexion
    private final Posicion posOrigen; //donde esta ubicada la escalera o el pasillo
    private final Plano planoDestino; //el plano destino
    private final Posicion posDestino; //posicion de llegada en el plano destino
    private final TipoConexion tipo; //guarda el enum para identificar si es escalera o pasillo

    public Conexion(Plano planoOrigen, Posicion posOrigen, Plano planoDestino, Posicion posDestino, TipoConexion tipo) {
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
    public TipoConexion getTipo() { return tipo; }

}
