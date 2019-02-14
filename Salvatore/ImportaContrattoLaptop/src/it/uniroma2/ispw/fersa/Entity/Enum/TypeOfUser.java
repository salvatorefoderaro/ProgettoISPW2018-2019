package it.uniroma2.ispw.fersa.Entity.Enum;

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

