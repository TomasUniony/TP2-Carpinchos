package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.utilidades;

/**
 * Contiene métodos auxiliares para realizar validaciones.
 */
public class ValidacionesUtiles {
    /**
     * Comprueba que un objeto sea distinto de null.
     * 
     * @param objeto objeto que se valida.
     * @param nombre nombre del objeto.
     */
    public static void validarDistintoDeNull(Object objeto, String nombre) {
        if (objeto == null) {
            throw new IllegalArgumentException("El parámetro " + nombre + " debe ser distinto de null");
        }
    }

    /**
     * Comprueba que una condición sea falsa.
     * 
     * @param valor   condición que debe ser falsa.
     * @param mensaje mensaje de error si la condición es verdadera.
     */
    public static void validarFalso(boolean valor, String mensaje) {
        if (valor) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
