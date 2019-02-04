package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

public enum RentableTypeEnum {
    APTTORENT ("Appartamento"),
    ROOMTORENT ("Camera"),
    BEDTORENT ("Letto");

    private final String name;


    private RentableTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
