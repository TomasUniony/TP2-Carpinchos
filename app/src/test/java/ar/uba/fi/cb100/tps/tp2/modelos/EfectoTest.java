package ar.uba.fi.cb100.tps.tp2.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ar.uba.fi.cb100.tps.tp2.modelos.efectos.AguaComoPiso;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.AtravesarParedes;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.EnemigosOcultos;
import ar.uba.fi.cb100.tps.tp2.modelos.efectos.LavaComoPiso;

class EfectoTest {

    private static final int COSTO_PISO = Terreno.PISO.costoEnergia();

    // equals y hashCode (alimentan la tabla hash de efectos activos)

    @Test
    void efectosDelMismoTipoSonIgualesYTienenElMismoHash() {
        Efecto uno = new AguaComoPiso();
        Efecto otro = new AguaComoPiso();

        assertEquals(uno, otro);
        assertEquals(uno.hashCode(), otro.hashCode());
    }

    @Test
    void efectosDeTiposDistintosNoSonIguales() {
        assertNotEquals(new AguaComoPiso(), new LavaComoPiso());
    }

    @Test
    void unEfectoNoEsIgualANuloNiAOtroTipoDeObjeto() {
        Efecto efecto = new AguaComoPiso();

        assertFalse(efecto.equals(null));
        assertFalse(efecto.equals("AGUA_COMO_PISO"));
    }

    @Test
    void elIdIdentificaAlTipo() {
        assertEquals("AGUA_COMO_PISO", new AguaComoPiso().id());
        assertEquals("LAVA_COMO_PISO", new LavaComoPiso().id());
        assertEquals("ATRAVESAR_PAREDES", new AtravesarParedes().id());
        assertEquals("ENEMIGOS_OCULTOS", new EnemigosOcultos().id());
    }

    @Test
    void rechazaIdNuloOVacio() {
        assertThrows(IllegalArgumentException.class, () -> efectoConId(null));
        assertThrows(IllegalArgumentException.class, () -> efectoConId(" "));
    }

    // Comportamiento por defecto

    @Test
    void unEfectoSinRedefinirNadaNoCambiaNada() {
        Efecto neutro = efectoConId("NEUTRO");

        assertEquals(8, neutro.costoDe(Terreno.LAVA, 8));
        assertFalse(neutro.permitePisar(Terreno.PARED));
        assertFalse(neutro.ocultaEnemigos());
    }

    // Efectos de terreno

    @Test
    void aguaComoPisoAbaratSoloElAgua() {
        Efecto efecto = new AguaComoPiso();

        assertEquals(COSTO_PISO, efecto.costoDe(Terreno.AGUA, Terreno.AGUA.costoEnergia()));
        assertEquals(Terreno.LAVA.costoEnergia(), efecto.costoDe(Terreno.LAVA, Terreno.LAVA.costoEnergia()));
        assertEquals(COSTO_PISO, efecto.costoDe(Terreno.PISO, COSTO_PISO));
    }

    @Test
    void lavaComoPisoAbaratSoloLaLava() {
        Efecto efecto = new LavaComoPiso();

        assertEquals(COSTO_PISO, efecto.costoDe(Terreno.LAVA, Terreno.LAVA.costoEnergia()));
        assertEquals(Terreno.AGUA.costoEnergia(), efecto.costoDe(Terreno.AGUA, Terreno.AGUA.costoEnergia()));
    }

    @Test
    void atravesarParedesPermitePisarSoloLaPared() {
        Efecto efecto = new AtravesarParedes();

        assertTrue(efecto.permitePisar(Terreno.PARED));
        assertFalse(efecto.permitePisar(Terreno.PISO));
        assertFalse(efecto.permitePisar(Terreno.AGUA));
    }

    @Test
    void atravesarParedesCuestaComoPisoYNoCambiaOtrosTerrenos() {
        Efecto efecto = new AtravesarParedes();

        assertEquals(COSTO_PISO, efecto.costoDe(Terreno.PARED, Terreno.PARED.costoEnergia()));
        assertEquals(Terreno.LAVA.costoEnergia(), efecto.costoDe(Terreno.LAVA, Terreno.LAVA.costoEnergia()));
    }

    @Test
    void losEfectosDeTerrenoNoOcultanEnemigos() {
        assertFalse(new AguaComoPiso().ocultaEnemigos());
        assertFalse(new LavaComoPiso().ocultaEnemigos());
        assertFalse(new AtravesarParedes().ocultaEnemigos());
    }

    // Efecto sobre la vista

    @Test
    void enemigosOcultosOcultaALosEnemigosYNoTocaElTerreno() {
        Efecto efecto = new EnemigosOcultos();

        assertTrue(efecto.ocultaEnemigos());
        assertFalse(efecto.permitePisar(Terreno.PARED));
        assertEquals(Terreno.AGUA.costoEnergia(), efecto.costoDe(Terreno.AGUA, Terreno.AGUA.costoEnergia()));
    }

    /** Efecto de prueba para poder crear uno con cualquier id. */
    private static Efecto efectoConId(String id) {
        return new Efecto(id) { };
    }
}