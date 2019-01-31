package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

public class Service {
    private int id;
    private String name;
    private String descriprion;
    private int price;

    public Service(int id, String name, String descriprion, int price) {
        this.id = id;
        this.name = name;
        this.descriprion = descriprion;
        this.price = price;
    }

    public int getId() {
        return id;
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

