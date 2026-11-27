package ar.uba.fi.cb100.tps.tp2.modelos.cofres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import ar.uba.fi.cb100.tps.tp2.modelos.Cofre;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.EnemigosOcultos;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.LavaComoPiso;
import ar.uba.fi.cb100.tps.tp2.modelos.items.CapaDeIntangibilidad;
import ar.uba.fi.cb100.tps.tp2.modelos.items.PocionDeEnergia;
import ar.uba.fi.cb100.tps.tp2.modelos.items.ZapatosDeAgua;

/**
 * Catalogo de tipos de cofre: tabla hash nombre -> tipoDeCofre.
 * Permite crear un cofre por nombre o sortear uno al azar.
 */
public class CatalogoDeCofres {

    private final Map<String, Supplier<Cofre>> catalogo = new HashMap<>();

    //Catalogo con todos los tipos de cofre del juego.
    public CatalogoDeCofres() {
        // (id, constructor)
        this.catalogo.put("COFRE_DE_PUNTOS", CofreDePuntos::new);
        this.catalogo.put("ZAPATOS_DE_AGUA", () -> new CofreDeItem(new ZapatosDeAgua()));
        this.catalogo.put("POCION_DE_ENERGIA", () -> new CofreDeItem(new PocionDeEnergia()));
        this.catalogo.put("CAPA_DE_INTANGIBILIDAD", () -> new CofreDeItem(new CapaDeIntangibilidad()));
        this.catalogo.put("LAVA_COMO_PISO", () -> new CofreDeEfecto(new LavaComoPiso(), 15));
        this.catalogo.put("ENEMIGOS_OCULTOS", () -> new CofreDeEfecto(new EnemigosOcultos(), 20));
        this.catalogo.put("TRAMPA", () -> new CofreTrampa(20));
    }

    /**
     * Crea un cofre nuevo del tipo indicado.
     *
     * @param nombre nombre del tipo (por ejemplo "TRAMPA")
     * @return un cofre nuevo
     * @throws IllegalArgumentException si el nombre no esta en el catalogo
     */
    public Cofre crear(String nombre) {
        //busco el tipo de cofre en el catalogo y devuelva la receta para crear uno nuevo. Si no existe, lanzo una excepcion.
        Supplier<Cofre> tipoDeCofre = this.catalogo.get(nombre);
        if (tipoDeCofre == null) {
            throw new IllegalArgumentException("Tipo de cofre desconocido: " + nombre);
        }
        //recién acá se crea el nuevo cofre, usando la receta obtenida del catalogo.
        return tipoDeCofre.get();
    }

    /**
     * Sortea un tipo al azar y crea un cofre nuevo de ese tipo.
     *
     * @param azar generador de numeros aleatorios (con semilla, para que sea reproducible)
     * @return un cofre nuevo de tipo sorteado
     */
    public Cofre crearAlAzar(Random azar) {
        //tomo las claves del catalogo, las ordeno y elijo una al azar. 
        //Esto asegura que con la misma semilla se obtenga siempre el mismo resultado.
        List<String> nombres = new ArrayList<>(this.catalogo.keySet());
        Collections.sort(nombres); // orden fijo: un HashMap no garantiza orden y la semilla dejaria de reproducir
        return this.crear(nombres.get(azar.nextInt(nombres.size())));
    }
}