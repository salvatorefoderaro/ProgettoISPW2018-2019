package Bean;

import Entity.OptionalService;
import Entity.TypeOfPayment;

import java.time.LocalDate;
import java.util.List;

public class contractBean {
    private int contractId; // importante!
    private boolean isExipired;
    private LocalDate initDate; // importante!
    private LocalDate terminationDate; // importante!
    private TypeOfPayment paymentMethod; // importante!
    private String tenantNickname; // importante!
    private String renterNickname;// importante!
    private String tenantCF;
    private String renterCF;
    private int grossPrice; // Prezzo rata + costi servizi; importante!
    private int netPrice; // prezzo netto per l'affitto
    private int frequencyOfPayment; // Mesi
    private boolean reported; // Serve per il Termina contratto, se c'è già una segnalazione pendente
    private List<OptionalService> serviceList; // importante!


    public contractBean(int contractId, boolean isExpired, LocalDate initDate, LocalDate terminationDate,
                    TypeOfPayment paymentMethod, String tenantName, String renterName, String tenantCF,
                    String renterCF, int grossPrice, int netPrice, int frequencyOfPayment, boolean reported,
                    List<OptionalService> serviceList) {

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

    public int getContractId() {
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

    public TypeOfPayment getPaymentMethod() {
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
}