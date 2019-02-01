package Entity;

public enum TypeOfRentable {
    APARTMENT ("APTTORENT"),
    BED ("BEDTORENT"),
    ROOM ("ROOMTORENT")
    ;

    private final String shortCode;

    TypeOfRentable(String code) {
        this.shortCode = code;
    }

    public String getType() {
        return this.shortCode;
    }
}

