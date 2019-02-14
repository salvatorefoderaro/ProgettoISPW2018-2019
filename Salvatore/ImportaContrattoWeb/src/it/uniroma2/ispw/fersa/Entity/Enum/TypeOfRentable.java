package it.uniroma2.ispw.fersa.Entity.Enum;

public enum TypeOfRentable {
    APARTMENT ("APTTORENT"),
    BED ("BEDTORENT"),
    ROOM ("ROOMTORENT")
    ;

    private final String type;

    TypeOfRentable(String type) {
        this.type = type;
    }

    public static TypeOfRentable fromString(String text) {
        for (TypeOfRentable b : TypeOfRentable.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getType() {
        return this.type;
    }

}

