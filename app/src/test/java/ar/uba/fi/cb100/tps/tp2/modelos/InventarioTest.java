package ar.uba.fi.cb100.tps.tp2.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InventarioTest {

    private static final Item ZAPATOS = Item.ZAPATOS_DE_AGUA;

    @Test
    void inventarioNuevoEstaVacio() {
        Inventario inventario = new Inventario();
        assertTrue(inventario.estaVacio());
        assertEquals(0, inventario.cantidad(ZAPATOS));
        assertFalse(inventario.contiene(ZAPATOS));
    }

    @Test
    void agregarSumaUnidades() {
        Inventario inventario = new Inventario();
        inventario.agregar(ZAPATOS);
        inventario.agregar(ZAPATOS);
        inventario.agregar(ZAPATOS, 3);

        assertEquals(5, inventario.cantidad(ZAPATOS));
        assertTrue(inventario.contiene(ZAPATOS));
    }

    @Test
    void quitarRestaUnaUnidad() {
        Inventario inventario = new Inventario();
        inventario.agregar(ZAPATOS, 2);

        assertTrue(inventario.quitar(ZAPATOS));

        assertEquals(1, inventario.cantidad(ZAPATOS));
    }

    @Test
    void quitarLaUltimaUnidadEliminaElItem() {
        Inventario inventario = new Inventario();
        inventario.agregar(ZAPATOS);

        assertTrue(inventario.quitar(ZAPATOS));

        assertFalse(inventario.contiene(ZAPATOS));
        assertTrue(inventario.estaVacio());
    }

    @Test
    void quitarUnItemQueNoSeTieneDevuelveFalse() {
        Inventario inventario = new Inventario();
        assertFalse(inventario.quitar(ZAPATOS));
    }

    @Test
    void rechazaCantidadNoPositiva() {
        Inventario inventario = new Inventario();
        assertThrows(IllegalArgumentException.class, () -> inventario.agregar(ZAPATOS, 0));
        assertThrows(IllegalArgumentException.class, () -> inventario.agregar(ZAPATOS, -1));
    }

    @Test
    void rechazaTipoNulo() {
        Inventario inventario = new Inventario();
        assertThrows(IllegalArgumentException.class, () -> inventario.agregar(null));
    }

    @Test
    void comoMapaNoPermiteModificarElInventario() {
        Inventario inventario = new Inventario();
        inventario.agregar(ZAPATOS);

        assertThrows(UnsupportedOperationException.class,
                () -> inventario.comoMapa().put(ZAPATOS, 99));
    }
}