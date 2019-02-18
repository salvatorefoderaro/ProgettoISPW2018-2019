package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;

import java.time.LocalDate;

public class RentingContract {
    private ContractId contractId;
    private int propertyId;
    private String renterNickname;
    private String tenantNickname;
    private LocalDate stipulationDate;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int propertyPrice;
    private int deposit;

    public RentingContract(ContractId contractId, int apartmentId, String renterNickname, String tenantNickname,
                           LocalDate stipulationDate, LocalDate creationDate, LocalDate startDate, LocalDate endDate,
                           int propertyPrice, int deposit) {
        this.contractId = contractId;
        this.propertyId = apartmentId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.stipulationDate = stipulationDate;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.propertyPrice = propertyPrice;
        this.deposit = deposit;
    }

    public ContractId getContractId() {
        return contractId;
    }

    public int getApartmentId() {
        return propertyId;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public LocalDate getStipulationDate() {
        return stipulationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPropertyPrice() {
        return propertyPrice;
    }

    public int getDeposit() {
        return deposit;
    }
}
