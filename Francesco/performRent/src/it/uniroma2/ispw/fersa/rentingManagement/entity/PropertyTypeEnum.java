package it.uniroma2.ispw.fersa.rentingManagement.entity;

public enum PropertyTypeEnum {
    APTTORENT ("Appartamento"),
    ROOMTORENT ("Camera"),
    BEDTORENT ("Letto");

    private final String name;


    private PropertyTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
