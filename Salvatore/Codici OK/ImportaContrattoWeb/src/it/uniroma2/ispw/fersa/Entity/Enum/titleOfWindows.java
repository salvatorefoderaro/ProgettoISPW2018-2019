package it.uniroma2.ispw.fersa.Entity.Enum;

public enum titleOfWindows {
    IMPORTCONTRACT ("Importa contratto - Importa contratto - FERSA"),
    LOGIN ("Login - Importa contratto - FERSA"),
    SEERENTABLE("Visualizza risorse affittabili - Importa contratto - FERSA")
    ;

    private final String toString;

    titleOfWindows(String string) {
        this.toString = string;
    }

    public String getString() {
        return this.toString;
    }

}

