package ar.uba.fi.cb100.tps.tp2.modelos;

//Reciclaje del dificultad de GeneradorCarpincho
public enum Dificultad {
    //nombre, porcentaje del agua, porcentaje de la lava
    FACIL("FACIL", 0.05, 0.00),
    MEDIO("MEDIO", 0.08, 0.03),
    DIFICIL("DIFICIL", 0.10, 0.06);

    private final String nombreDificultad;
    private final double porcentajeAgua;
    private final double porcentajeLava;

    Dificultad(String nombreDificultad, double porcentajeAgua, double porcentajeLava) {
        this.nombreDificultad = nombreDificultad;
        this.porcentajeAgua = porcentajeAgua;
        this.porcentajeLava = porcentajeLava;
    }

    public String nombreDificultad() {
        return nombreDificultad;
    }

    public double porcentajeAgua() {
        return porcentajeAgua;
    }

    public double porcentajeLava() {
        return porcentajeLava;
    }

}
