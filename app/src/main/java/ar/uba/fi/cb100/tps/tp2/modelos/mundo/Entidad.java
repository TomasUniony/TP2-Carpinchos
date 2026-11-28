package ar.uba.fi.cb100.tps.tp2.modelos.mundo;

public enum Entidad {
    MONSTRUO('M', false),
    COFRE('C', false),
    HEROE('E', false);

    private final char simbolo;
    private final boolean permitePaso;

    Entidad(char simbolo, boolean permitePaso) {
        this.simbolo = simbolo;
        this.permitePaso = permitePaso;
    }

    public char simbolo() {
        return simbolo;
    }

    public boolean permitePaso() {
        return permitePaso;
    }

    public static Entidad desdeCaracter(char caracter) {
        for (Entidad entidad : values()) {
            if (entidad.simbolo == caracter) {
                return entidad;
            }
        }
        return null;
    }
}