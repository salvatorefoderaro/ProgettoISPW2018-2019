package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfRentable {
    APARTMENT ("APTTORENT"),
    BED ("BEDTORENT"),
    ROOM ("ROOMTORENT")
    ;

    private final String shortCode;

    typeOfRentable(String code) {
        this.shortCode = code;
    }

    public String getType() {
        return this.shortCode;
    }
}

