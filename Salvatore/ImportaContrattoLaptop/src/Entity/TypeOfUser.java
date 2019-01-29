package Entity;

public enum TypeOfUser {
    RENTER ("renter"),
    TENANT ("tenant"),
    ;

    private final String shortCode;

    TypeOfUser(String code) {
        this.shortCode = code;
    }

    public String getDirectionCode() {
        return this.shortCode;
    }
}

