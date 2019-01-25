package Bean;



import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import Entity.optionalService;
import Entity.typeOfPayment;
import java.util.List;

/**
 * di fatto è un bean che ha l'unico scopo di contenere le informazioni
 * È  importante che non ci siano setter! I parametri si definiscono tutti nel costruttore.
 */
public class contractBean implements Serializable {
    private int contractId; // importante!
    private boolean isExipired;
    private LocalDate initDate; // importante!
    private LocalDate terminationDate; // importante!
    private typeOfPayment paymentMethod; // importante!
    private String tenantNickname; // importante!
    private String renterNickname;// importante!
    private String tenantCF;
    private String renterCF;
    private int grossPrice; // Prezzo rata + costi servizi; importante!
    private int netPrice; // prezzo netto per l'affitto
    private int frequencyOfPayment; // Mesi
    private boolean reported; // Serve per il Termina contratto, se c'è già una segnalazione pendente
    private List<optionalService> serviceList; // importante!


    public contractBean(int contractId, boolean isExpired, LocalDate initDate, LocalDate terminationDate,
                    typeOfPayment paymentMethod, String tenantName, String renterName, String tenantCF,
                    String renterCF, int grossPrice, int netPrice, int frequencyOfPayment, boolean reported,
                    List<optionalService> serviceList) {
        this.contractId = contractId;
        this.isExipired = isExpired;
        this.initDate = initDate;
        this.terminationDate = terminationDate;
        this.paymentMethod = paymentMethod;
        this.tenantNickname = tenantName;
        this.renterNickname = renterName;
        this.tenantCF = tenantCF;
        this.renterCF = renterCF;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
        this.frequencyOfPayment = frequencyOfPayment;
        this.reported = reported;
        this.serviceList = serviceList;
    }

    public int  getContractId() {
        return contractId;
    }

    public boolean isExipired() {
        return isExipired;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public typeOfPayment getPaymentMethod() {
        return paymentMethod;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public String getTenantCF() {
        return tenantCF;
    }

    public String getRenterCF() {
        return renterCF;
    }

    public int getGrossPrice() {
        return grossPrice;
    }

    public int getNetPrice() {
        return netPrice;
    }

    public int getFrequencyOfPayment() {
        return frequencyOfPayment;
    }

    public boolean isReported() {
        return reported;
    }

    /**
     * non posso usare direttamente clone() perché farebbe una shallow copy e ritornerebbe l'indirizzo della lista
     * e chiunque potrebbe mofdificarla dall'esterno!!
     * @return List <OptionalService>
     */
    public List<optionalService> getServiceList() {
        List<optionalService> deepCopy = new ArrayList<>(serviceList);
        //  deepCopy.addAll(serviceList);
        return deepCopy;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", isExipired=" + isExipired +
                ", initDate=" + initDate +
                ", terminationDate=" + terminationDate +
                ", paymentMethod=" + paymentMethod +
                ", tenantNickname='" + tenantNickname + '\'' +
                ", renterNickname='" + renterNickname + '\'' +
                ", tenantCF='" + tenantCF + '\'' +
                ", renterCF='" + renterCF + '\'' +
                ", grossPrice=" + grossPrice +
                ", netPrice=" + netPrice +
                ", frequencyOfPayment=" + frequencyOfPayment +
                ", reported=" + reported +
                ", serviceList=" + serviceList +
                '}';
    }
}