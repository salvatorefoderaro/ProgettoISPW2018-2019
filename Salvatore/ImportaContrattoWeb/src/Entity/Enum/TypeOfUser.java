package Entity.Enum;

public enum TypeOfUser {
    RENTER ("renter"),
    TENANT ("tenant"),
    ;

    private final String type;

    TypeOfUser(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}

