package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

public class Service {
    private String name;
    private String descriprion;
    private int price;

    public Service(String name, String descriprion, int price) {
        this.name = name;
        this.descriprion = descriprion;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescriprion() {
        return descriprion;
    }

    public int getPrice() {
        return price;
    }
}

