package ar.uba.fi.cb100.tps.tp2.modelos;

//Reciclaje del dificultad de GeneradorCarpincho
public enum Dificultad {
    //nombre, porcentaje del agua, porcentaje de la lava
    FACIL("FACIL", 1, 1, 25, 40, 12, 4, 0, 0),
    MEDIO("MEDIO", 1, 3, 15, 20, 6, 6, 2, 0),
    DIFICIL("DIFICIL", 3, 3, 15, 20, 3, 10, 6, 3);

    private final String nombreDificultad;
    private final int cantTableros;
    private final int pisosPorTablero;
    private final int filas;
    private final int columnas;
    private final int cantCofres;
    private final int cantMonstruos;
    private final int cantEscaleras;
    private final int cantPasillos;

    Dificultad(String nombreDificultad, int cantTableros, int pisosPorTablero, int filas, int columnas,
               int cantCofres, int cantMonstruos, int cantEscaleras, int cantPasillos) {
        this.nombreDificultad = nombreDificultad;
        this.cantTableros = cantTableros;
        this.pisosPorTablero = pisosPorTablero;
        this.filas = filas;
        this.columnas = columnas;
        this.cantCofres = cantCofres;
        this.cantMonstruos = cantMonstruos;
        this.cantEscaleras = cantEscaleras;
        this.cantPasillos = cantPasillos;

    }

    public String nombreDificultad() {
        return nombreDificultad;
    }

    public int cantTableros() {
        return cantTableros;
    }

    public int pisosPorTablero() {
        return pisosPorTablero;
    }

    public int filas() { return filas; }

    public int columnas() { return columnas; }

    public int cantCofres() { return cantCofres; }

    public int cantMonstruos() { return cantMonstruos; }

    public int cantEscalesras() { return cantEscaleras; }

    public int cantPasillos() { return cantPasillos; }

    public static Dificultad desdeNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre de la dificultad no puede ser nulo.");
        }
        for (Dificultad d : values()) {
            if (d.name().equalsIgnoreCase(nombre.trim())) {
                return d;
            }
        }
        throw new IllegalArgumentException("Dificultad inválida: " + nombre);
    }

}
