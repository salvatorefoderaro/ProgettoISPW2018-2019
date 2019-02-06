package Bean;

public class optionalServiceBean {
    private  String serviceName;
    private String serviceDescription;
    private int servicePrice;


    public String getServiceName() { return serviceName; }

    public String getServiceDescription() { return serviceDescription; }

    public int getServicePrice() { return servicePrice; }

    public void setServiceName(String serviceName){ this.serviceName = serviceName; }

    public void setServiceDescription(String serviceDescription){ this.serviceDescription = serviceDescription; }

    public void setServicePrice(int servicePrice){ this.servicePrice = servicePrice; }
}
