package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import java.time.LocalDate;
import java.util.List;

public class ContractRequestBean {

    private String contractName;
    private String rentableName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rentablePrice;
    private int deposit;
    private List<ServiceBean> services;
    private int total;

    public ContractRequestBean(String contractName, String rentableName, LocalDate startDate, LocalDate endDate, int rentablePrice, int deposit, List<ServiceBean> services, int total) {
        this.contractName = contractName;
        this.rentableName = rentableName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentablePrice = rentablePrice;
        this.deposit = deposit;
        this.services = services;
        this.total = total;
    }

    public String getContractName() {
        return contractName;
    }

    public String getRentableName() {
        return rentableName;
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

    public List<ServiceBean> getServices() {
        return services;
    }

    public int getTotal() {
        return this.total;
    }
}
