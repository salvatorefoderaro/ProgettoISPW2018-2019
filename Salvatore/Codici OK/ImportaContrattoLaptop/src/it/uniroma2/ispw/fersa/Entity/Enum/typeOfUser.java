package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfUser {
    RENTER ("RENTER"),
    TENANT ("TENANT"),
    ;

    private final String shortCode;

    typeOfUser(String code) {
        this.shortCode = code;
    }

    public String getType() {
        return this.shortCode;
    }
}

