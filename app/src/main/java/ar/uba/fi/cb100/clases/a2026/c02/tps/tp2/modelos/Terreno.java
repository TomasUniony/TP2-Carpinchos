package ar.uba.fi.cb100.clases.a2026.c02.tps.tp2.modelos;

public enum Terreno {
    PARED('#', false, 0),
    PISO('.', true, 1),
    AGUA('~', true, 3),
    LAVA('^', true, 8),
    ENTRADA('E', true, 1),
    SALIDA('S', true, 1);

    private final char simbolo;
    private final boolean transitable;
    private final int costoEnergia;

    Terreno(char simbolo, boolean transitable, int costoEnergia) {
        this.simbolo = simbolo;
        this.transitable = transitable;
        this.costoEnergia = costoEnergia;
    }

    public char simbolo() {
        return this.simbolo;
    }

    public boolean esTransitable() {
        return this.transitable;
    }

    public int costoEnergia() {
        return this.costoEnergia;
    }

    public static Terreno simbolo(char simbolo) {
        for (Terreno t : values()) {
            if (t.simbolo == simbolo) {
                return t;
            }
        }
        return PISO; //Esto seria en el caso de 'C' o 'M', son transitables.
    }

}
