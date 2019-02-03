package Entity;

public enum TypeOfUser {
    RENTER ("RENTER"),
    TENANT ("TENANT"),
    ;

    private final String shortCode;

    TypeOfUser(String code) {
        this.shortCode = code;
    }

    public String getType() {
        return this.shortCode;
    }
}

