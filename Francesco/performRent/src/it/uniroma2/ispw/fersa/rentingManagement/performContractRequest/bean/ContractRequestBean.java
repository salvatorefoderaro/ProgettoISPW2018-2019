package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import java.time.LocalDate;

public class ContractRequestBean {
    private String renterNickname;
    private String tenantNickname;
    private int rentalFeatureId;
    private int contractTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rentablePrice;
    private int deposit;
    private int serviceIds[];


    public ContractRequestBean(String renterNickname, String tenantNickname, int rentalFeatureId,int contractTypeId, LocalDate startDate, LocalDate endDate, int rentablePrice, int deposit, int serviceIds[]){
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.rentalFeatureId = rentalFeatureId;
        this.contractTypeId = contractTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentablePrice = rentablePrice;
        this.deposit = deposit;
        this.serviceIds = serviceIds;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public int getRentalFeatureId() {
        return rentalFeatureId;
    }

    public int getContractTypeId() {
        return contractTypeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getRentablePrice() {
        return rentablePrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public int[] getServiceIds() {
        return serviceIds;
    }
}
