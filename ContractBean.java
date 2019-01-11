package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ContractBean implements Serializable {
    private long contractId; // Termina contratto
    private boolean isExipired; // Termina contratto
    private LocalDate initDate; // Termina contratto
    private LocalDate terminationDate; // Termina contratto
    private TypeOfPayment paymentMethod;
    private String lessorName; // Termina contratto
    private String renterName; // Termina contratto
    private String lessorCF;
    private String renterCF;
    private int price;
    private int frequencyOfPayment;

    /**
     * Default Constructor
     * We have default values
     * (usiamo valori di default per evitare di fare i controlli sui null dopo.
     * Successivamente si vanno a popolare i campi del bean tramite setter)
     */
    public ContractBean() {
        this.contractId = this.hashCode(); 
        this.initDate = LocalDate.now(); 
        this.terminationDate = LocalDate.now();
        this.paymentMethod = TypeOfPayment.CREDIT_CARD;
        this.price= 0;
        this.frequencyOfPayment= 0;
        this.lessorName = ""; 
        this.renterName = ""; 
        this.lessorCF = "";
        this.renterCF = "";
    }

    public int getFrequencyOfPayment() {
        return frequencyOfPayment;
    }

    public void setFrequencyOfPayment(int frequencyOfPayment) {
        this.frequencyOfPayment = frequencyOfPayment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public boolean isExipired() {
        return isExipired;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public TypeOfPayment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(TypeOfPayment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getLessorName() {
        return lessorName;
    }

    public void setLessorName(String lessorName) {
        this.lessorName = lessorName;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getLessorCF() {
        return lessorCF;
    }

    public void setLessorCF(String lessorCF) {
        this.lessorCF = lessorCF;
    }

    public String getRenterCF() {
        return renterCF;
    }

    public void setRenterCF(String renterCF) {
        this.renterCF = renterCF;
    }

    /**
     * automatic equals method
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractBean that = (ContractBean) o;
        return contractId == that.contractId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractId);
    }
}
