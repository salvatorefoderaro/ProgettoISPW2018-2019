package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfRentable {
    APARTMENT ("APTTORENT"),
    BED ("BEDTORENT"),
    ROOM ("ROOMTORENT")
    ;

    private final String type;

    typeOfRentable(String type) {
        this.type = type;
    }

    public static typeOfRentable fromString(String text) {
        for (typeOfRentable b : values()) {
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

