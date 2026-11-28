package ar.uba.fi.cb100.tps.tp2.modelos.mundo;

public enum Terreno {
    PARED('#', false, 0),
    PISO('.', true, 1),
    AGUA('~', true, 3),
    LAVA('^', true, 8),
    ENTRADA('E', true, 1),
    SALIDA('S', true, 1),
    ESCALERA('H', true, 1),
    PASILLO('P', true, 1);

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

    public boolean esLibreParaConexion() {  //para crear conexiones sin reemplazar la entrada ni salida
        return esTransitable() && this != ENTRADA && this != SALIDA && this != ESCALERA && this != PASILLO;
    }

    public int costoEnergia() {
        return this.costoEnergia;
    }

    public static Terreno desdeSimbolo(char simbolo) {
        for (Terreno t : values()) {
            if (t.simbolo == simbolo) {
                return t;
            }
        }
        return null; //En el plano se cambia por piso, y se le añade monstruo
    }

}
