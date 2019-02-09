package Entity;

public enum TitleOfWindows {
    IMPORTCONTRACT ("Importa contratto - Importa contratto - FERSA"),
    LOGIN ("Login - Importa contratto - FERSA"),
    SEERENTABLE("Visualizza risorse affittabili - Importa contratto - FERSA")
    ;

    private final String toString;

    TitleOfWindows(String string) {
        this.toString = string;
    }

    public String getString() {
        return this.toString;
    }

}

