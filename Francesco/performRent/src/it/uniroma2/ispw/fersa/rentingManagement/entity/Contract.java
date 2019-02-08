package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.time.LocalDate;
import java.util.List;

public class Contract {

    private int contractId;
    private int rentableId;
    private ContractStateEnum state;
    private String tenantNickname;
    private LocalDate creationDate;
    private LocalDate stipulationDate;
    private IntervalDate intervalDate;
    private String tenantName;
    private String tenantSurname;
    private String tenantCF;
    private LocalDate tenantDateOfBirth;
    private String tenantCityOfBirth;
    private String tenantAddress;
    private String renterName;
    private String renterSurname;
    private String renterCF;
    private String renterAddress;
    private int propertyPrice;
    private int deposit;
    private ContractType contractType;
    private List<Service> services;


    public Contract(int rentableId, String tenantNickname, LocalDate startDate, LocalDate endDate,
                    String tenantName, String tenantSurname, String tenantCF, LocalDate tenantDateOfBirth,
                    String tenantCityOfBirth, String tenantAddress, String renterName,
                    String renterSurname, String renterCF, String renterAddress, int propertyPrice, int deposit,
                    ContractType contractType, List<Service> services){
        this.rentableId = rentableId;
        this.tenantNickname = tenantNickname;
        this.intervalDate = new IntervalDate(startDate, endDate);
        this.tenantName = tenantName;
        this.tenantSurname = tenantSurname;
        this.tenantCF = tenantCF;
        this.tenantDateOfBirth = tenantDateOfBirth;
        this.tenantCityOfBirth = tenantCityOfBirth;
        this.tenantAddress = tenantAddress;
        this.renterName = renterName;
        this.renterSurname = renterSurname;
        this.renterCF = renterCF;
        this.renterAddress = renterAddress;
        this.propertyPrice = propertyPrice;
        this.deposit = deposit;
        this.contractType = contractType;
        this.services = services;

    }

    public int getContractId() {
        return contractId;
    }

    public int getRentableId() {
        return rentableId;
    }

    public ContractStateEnum getState() {
        return state;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getStipulationDate() {
        return stipulationDate;
    }

    public LocalDate getStartDate() {
        return this.intervalDate.getBeginDate();
    }

    public LocalDate getEndDate() {
        return this.intervalDate.getEndDate();
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getTenantSurname() {
        return tenantSurname;
    }

    public String getTenantCF() {
        return tenantCF;
    }

    public LocalDate getTenantDateOfBirth() {
        return tenantDateOfBirth;
    }

    public String getTenantCityOfBirth() {
        return tenantCityOfBirth;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public String getRenterName() {
        return renterName;
    }

    public String getRenterSurname() {
        return renterSurname;
    }

    public String getRenterCF(){
        return renterCF;
    }

    public String getRenterAddress() {
        return renterAddress;
    }

    public int getPropertyPrice() {
        return propertyPrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<Service> getServices() {
        return services;
    }

    public String getContractTypeName() {
        return this.contractType.getName();
    }

    public int getNumMonths() {
        return (int) this.intervalDate.getNumMonths();
    }

    public boolean isTransitory(){
        return this.contractType.isTransitory();
    }

    public int getNetPrice() {
        return (int) (this.intervalDate.getNumMonths() * this.propertyPrice);
    }

    public int getGrossPrice() {
        int total = 0;

        total += getNetPrice();

        for (int i = 0; i < this.services.size(); i++) {
            total += (int) (intervalDate.getNumMonths() * this.services.get(i).getPrice());
        }

        return total;
    }

}
