package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

public class ServiceBean {

    private int serviceId;
    private String name;
    private String descriprion;
    private int price;

    public ServiceBean(int serviceId, String name, String descriprion, int price) {
        this.serviceId = serviceId;
        this.name = name;
        this.descriprion = descriprion;
        this.price = price;
    }

    public int getServiceId(){
        return this.serviceId;
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
