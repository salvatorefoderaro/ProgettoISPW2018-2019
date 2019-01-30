package Entity;

public enum TypeOfRentable {
    APARTMENT ("apt"),
    BED ("bed"),
    ROOM ("room")
    ;

    private final String shortCode;

    TypeOfRentable(String code) {
        this.shortCode = code;
    }

    public String getDirectionCode() {
        return this.shortCode;
    }
}

