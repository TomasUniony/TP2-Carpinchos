package ar.uba.fi.cb100.tps.tp2.modelos;


import ar.uba.fi.cb100.tps.tp2.GeneradorCarpincho;
import ar.uba.fi.cb100.tps.tp2.persistencia.GestorJson;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mazmorra {
    private final List<Plano> planos;
    private final Graph<Plano, Conexion> grafoMazmorra;
    private final List<Conexion> conexiones; //facilita buscar que conexion corresponde a una determinada posicion
    private final Plano planoSalida;
    private final Posicion posSalida;
    private Random azar;


    public Mazmorra(GeneradorCarpincho generador, Configuracion configuracion) {
        this.planos = generarPlanos(generador, configuracion); //genera los planos

        this.grafoMazmorra = new SimpleWeightedGraph<>(Conexion.class);  //grafo con peso y sin direcciones.
        for (Plano p : planos) {
            this.grafoMazmorra.addVertex(p); //añade los planos como vertices
        }

        this.conexiones = new ArrayList<>();
        construirConexiones(configuracion.dificultad());

        Plano planoSalidaAux = null;
        Posicion posSalidaAux = null;
        for (Plano p : planos) {
            if (p.tieneSalida()) {  //busca el plano con salida y su posicion
                planoSalidaAux = p;
                posSalidaAux = p.posicionSalida();
                break;
            }
        }

        if (planoSalidaAux == null) {
            throw new IllegalStateException("La mazmorra debe contener al menos un plano con casilla de salida.");
        }

        this.planoSalida = planoSalidaAux;
        this.posSalida = posSalidaAux;

        validarAlcanzabilidadGlobal();
    }

    //genera los planos con el generador, en nuestro caso el GeneradorCarpincho
    private List<Plano> generarPlanos(GeneradorCarpincho generador, Configuracion configuracion) {

        int cantidadPlanos = configuracion.dificultad().cantPlanos();
        if (cantidadPlanos <= 0) {
            throw new IllegalArgumentException("La cantidad de planos debe ser un entero positivo.");
        }

        List<Plano> planos = new ArrayList<>();

        //orquestador de semillas, se encarga de crear las semillas aleatorias y reproducir las partidas (si se inroduce una semilla)

        if (configuracion.semilla() != null) {
            this.azar = new Random(configuracion.semilla());
        } else {
            this.azar = new Random();
        }

        int cofresPorPlano = configuracion.dificultad().cantCofres() / cantidadPlanos; //Repartir cofres y monstruos totales
        int monstruosPorPlano = configuracion.dificultad().cantMonstruos() / cantidadPlanos; // entre la cantidad de planos

        for (int i = 0; i < cantidadPlanos; i++) {
            long semillaEstePlano = azar.nextLong(); //esto es lo que hace posible reproducir las partidas
            boolean conSalida = (i == cantidadPlanos - 1); //devuelve true si es el ultimo plano, donde deberia estar la salida.

            //arma el json de entrada especifico para este plano
            String entradaPlano = GestorJson.armarJsonEntradaPlano(configuracion.dificultad(), semillaEstePlano, conSalida, cofresPorPlano, monstruosPorPlano);

            String stringPlanoSalida = generador.generar(entradaPlano); //genera el plano
            GestorJson.guardarJson(stringPlanoSalida, i + 1); //guarda el plano

            String[] arregloPlano = GestorJson.extraerPlano(stringPlanoSalida); //parsea la respuesta
            planos.add(new Plano(arregloPlano)); //la añade al plano
        }

        return planos;
    }

    //se encarga de crear las conexiones entre planos
    private void construirConexiones(Dificultad dificultad) {
        int tableros = dificultad.cantTableros();
        int pisos = dificultad.pisosPorTablero();

        int pisoMedio = pisos / 2;
        int totalPlanos = this.planos.size();

        for (int i = 0; i < totalPlanos; i++) { //recorre todos los planos
            int t = i / pisos; //indice del tablero actual
            int p = i % pisos; //indice del piso actual

            if (p < pisos - 1) { //conecta con el piso superior del mismo tablero y creaa ESCALERAS
                crearConexion(this.planos.get(i), this.planos.get(i + 1), Terreno.ESCALERA);
            }

            if (tableros > 1 && p == pisoMedio) { //si es un piso medio, y hay mas de 1 tablero, crea el PASILLO
                int tableroSiguiente = (t + 1) % tableros;
                int planoDestino = (tableroSiguiente * pisos) + pisoMedio;

                crearConexion(this.planos.get(i), this.planos.get(planoDestino), Terreno.PASILLO);
            }
        }
    }

    private void crearConexion(Plano planoA, Plano planoB, Terreno tipo) {
        Posicion posicionA;
        Posicion posicionB;

        if (tipo == Terreno.ESCALERA) {
            posicionA = posicionB = planoA.buscarCeldaLibreCoincidente(planoB); //misma posicion para ambos
        } else if (tipo == Terreno.PASILLO) {

            List<Posicion> candidatosA = planoA.obtenerCandidatoBordeDerecho(); //se buscan todas las posibilidades de pasillos, para elegir una al azar
            List<Posicion> candidatosB = planoB.obtenerCandidatoBordeIzquierdo();

            posicionA = elegirCandidatoAlAzar(candidatosA);
            posicionB = elegirCandidatoAlAzar(candidatosB);
        } else {
            throw new IllegalArgumentException("Tipo de conexión no válido: " + tipo);
        }

        planoA.cambiarTerreno(posicionA, tipo);
        planoB.cambiarTerreno(posicionB, tipo);

        Conexion conexion = new Conexion(planoA, posicionA, planoB, posicionB, tipo);
        this.conexiones.add(conexion);
        this.grafoMazmorra.addEdge(planoA, planoB, conexion);
    }

    private Posicion elegirCandidatoAlAzar(List<Posicion> candidatos) { //elige un indice al azar
        if (candidatos == null || candidatos.isEmpty()) {
            throw new IllegalStateException("No hay posiciones candidatas para realizar la conexión.");
        }
        //nextInt(size) abarca todos los indices validos usando la semilla global, para poder reproducir la partida
        return candidatos.get(this.azar.nextInt(candidatos.size()));

    }

    //Verifica mediante el grafo del mundo que exista al menos una ruta transitable
    // (pasillos y escaleras) desde el plano inicial hasta el plano que contiene la salida.
    private void validarAlcanzabilidadGlobal() {
        Plano planoInicial = this.planos.getFirst(); // lo mismo que get(0)
        ConnectivityInspector<Plano, Conexion> inspector = new ConnectivityInspector<>(this.grafoMazmorra);

        if (!inspector.pathExists(planoInicial, this.planoSalida)) {
            throw new IllegalStateException("La mazmorra generada no posee un camino válido desde el plano inicial hasta la salida.");
        }
    }
}

