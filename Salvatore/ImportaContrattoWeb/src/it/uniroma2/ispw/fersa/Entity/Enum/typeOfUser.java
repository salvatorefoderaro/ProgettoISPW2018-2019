package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfUser {
    RENTER ("renter"),
    TENANT ("tenant"),
    ;

    private final String type;

    typeOfUser(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}

