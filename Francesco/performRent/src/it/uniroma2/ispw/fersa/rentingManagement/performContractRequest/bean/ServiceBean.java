package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

public class ServiceBean {

    private String name;
    private String descriprion;
    private int price;

    public ServiceBean(String name, String descriprion, int price) {
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
