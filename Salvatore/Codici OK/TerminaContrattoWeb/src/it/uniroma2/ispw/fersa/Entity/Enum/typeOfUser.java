package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfUser {
    RENTER ("RENTER"),
    TENANT ("TENANT"),
    NOTLOGGED("")
    ;

    private final String shortCode;

    typeOfUser(String string) {
        this.shortCode = string;
    }

    public String getString() {
        return this.shortCode;
    }

    public static typeOfUser getType(String value) {
        for (typeOfUser type : typeOfUser.values()) {
            if (type.getString().equals(value)){
                return type;
            }
        }
        return null;
    }
}

