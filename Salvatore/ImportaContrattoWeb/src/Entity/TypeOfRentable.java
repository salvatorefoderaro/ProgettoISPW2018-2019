package Entity;

public enum TypeOfRentable {
    APARTMENT ("apt"),
    BED ("bed"),
    ROOM ("room")
    ;

    private final String type;

    TypeOfRentable(String type) {
        this.type = type;
    }

    public static TypeOfRentable makeType(String value){
        for (TypeOfRentable type : values())
            if (type.getType() == value )
                return type;
        return null;
    }

    public String getType() {
        return this.type;
    }

}

