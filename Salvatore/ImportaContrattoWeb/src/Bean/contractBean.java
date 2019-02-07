package Bean;

import Entity.OptionalService;
import Entity.TypeOfContract;
import Entity.TypeOfPayment;
import Entity.TypeOfRentable;

import java.time.LocalDate;
import java.util.List;

public class contractBean {

    private int contractId; // importante!
    private int rentableId;
    private LocalDate initDate; // importante!
    private LocalDate terminationDate; // importante!
    private TypeOfPayment paymentMethod; // importante!
    private String tenantNickname; // importante!
    private String renterNickname;// importante!
    private String tenantCF;
    private String renterCF;
    private String renterAddress;
    private String tenantAddress;
    private String renterName;
    private String tenantName;
    private String renterSurname;
    private String tenantSurname;
    private int deposito;
    private int grossPrice; // Prezzo rata + costi servizi; importante!
    private int netPrice; // prezzo netto per l'affitto
    private int frequencyOfPayment; // Mesi
    private boolean reported; // Serve per il Termina contratto, se c'è già una segnalazione pendente
    private List<OptionalService> serviceList; // importante!
    private TypeOfRentable rentableType;
    private boolean JDBCcommit;
    private TypeOfContract contractType;

    public contractBean(int contractId, int rentableId, boolean isExpired, LocalDate initDate, LocalDate terminationDate,
                        TypeOfPayment paymentMethod, String tenantNickname, String renterNickname, String renterName,
                        String tenantName, String tenantCF,
                        String renterCF, String renterAddress, String tenantnAddress, String renterSurname, String tenantSurname, int grossPrice, int netPrice, int frequencyOfPayment, boolean reported,
                        List<OptionalService> serviceList, TypeOfRentable rentableType, int deposito, TypeOfContract contractType) {

        this.contractId = contractId;
        this.initDate = initDate;
        this.terminationDate = terminationDate;
        this.paymentMethod = paymentMethod;
        this.tenantNickname = tenantNickname;
        this.renterNickname = renterNickname;
        this.tenantCF = tenantCF;
        this.renterCF = renterCF;
        this.renterAddress = renterAddress;
        this.renterName = renterName;
        this.renterSurname = renterSurname;
        this.renterAddress = renterAddress;
        this.tenantAddress = tenantnAddress;
        this.tenantName = tenantName;
        this.tenantSurname = tenantSurname;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
        this.frequencyOfPayment = frequencyOfPayment;
        this.reported = reported;
        this.serviceList = serviceList;
        this.rentableType = rentableType;
        this.rentableId = rentableId;
        this.deposito = deposito;
        this.contractType = contractType;
    }

    public void setJDBCcommit(boolean JDBCcommit){
        this.JDBCcommit = JDBCcommit;
    }

    public boolean getJDBCcommit(){ return this.JDBCcommit; }
    public String getTenantName(){ return this.tenantName; }
    public String getTenantSurname(){ return this.tenantSurname; }
    public String getTenantAddress(){ return this.tenantAddress; }
    public String getRenterName(){ return this.renterName; }
    public String getRenterSurname(){ return this.renterSurname; }
    public String getRenterAddress(){ return this.renterAddress; }
    public int getDeposito(){ return this.deposito; }
    public int getRentableId(){ return this.rentableId; }
    public int getContractId() {
        return contractId;
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
    public TypeOfRentable getRentableType(){ return rentableType; }
    public TypeOfContract getContractType(){ return contractType; }

}