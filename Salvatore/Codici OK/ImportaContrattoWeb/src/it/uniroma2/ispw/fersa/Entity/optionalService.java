package it.uniroma2.ispw.fersa.Entity;

import java.util.Objects;

public class optionalService {
    private  String serviceName;
    private String serviceDescription;
    private int servicePrice;

    public optionalService(String serviceName, String serviceDescription, int servicePrice) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    @Override
    public String toString() {
        return "optionalService{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", servicePrice=" + servicePrice +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        optionalService service = (optionalService) object;
        return Objects.equals(serviceName, service.serviceName);
    }
}
