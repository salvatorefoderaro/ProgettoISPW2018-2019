package it.uniroma2.ispw.fersa.Entity.Enum;

public enum TypeOfUser {
    RENTER ("RENTER"),
    TENANT ("TENANT"),
    NOTLOGGED("")
    ;

    private final String shortCode;

    TypeOfUser(String string) {
        this.shortCode = string;
    }

    public String getString() {
        return this.shortCode;
    }

    public static TypeOfUser getType(String value) {
        for (TypeOfUser type : TypeOfUser.values()) {
            if (type.getString().equals(value)){
                return type;
            }
        }
        return null;
    }
}

