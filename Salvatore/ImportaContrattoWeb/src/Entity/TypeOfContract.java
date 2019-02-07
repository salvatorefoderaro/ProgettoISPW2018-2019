package Entity;

public enum TypeOfContract {
    CONTRACT1 ("Contratto ordinario a canone libero", 1, 48, 96),
    CONTRACT2 ("Contratto transitorio", 2, 1, 18),
    CONTRACT3 ("Contratto di locazione convenzionato o a canone concordato", 3, 36,72),
    CONTRACT4 ("Contratto transitorio per studenti", 4, 6, 36)
    ;

    public final String type;
    public final int ID;
    public final int minDuration;
    public final int maxDuration;

    TypeOfContract(String type, int ID, int minDuration, int maxDuration) {
        this.type = type;
        this.ID = ID;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public static int idFromString(String text) {
        for (TypeOfContract b : TypeOfContract.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b.ID;
            }
        }
        return 0;
    }

    public static TypeOfContract typeFromString(String text) {
        for (TypeOfContract b : TypeOfContract.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


}

