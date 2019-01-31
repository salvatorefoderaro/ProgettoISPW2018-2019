package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractRequest {

    private int requestId;
    private String renterNickname;
    private String tenantNickname;
    private Rentable rentable;
    private ContractType contractType;
    private RequestStateEnum state;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private int deposit;
    private List<Service> services = new ArrayList<>();

    public ContractRequest (String renterNickname, String tenantNickname, Rentable rentable, int price, int deposit) {
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.rentable = rentable;
        this.price = price;
        this.deposit = deposit;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public void insertPeriod(LocalDate startDate, LocalDate endDate) throws PeriodException {
        if (this.contractType.checkPeriod(startDate, endDate)) throw new PeriodException();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }



}
