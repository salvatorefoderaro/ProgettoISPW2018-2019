package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RequestStateEnum;

import java.time.LocalDate;
import java.util.List;

public class ContractRequestInfoBean {


    private String contractName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rentablePrice;
    private int deposit;
    private List<ServiceBean> services;
    private int total;


    public ContractRequestInfoBean(String contractName, LocalDate startDate, LocalDate endDate, int rentablePrice, int deposit, List<ServiceBean> services, int total) {
        this.contractName = contractName;
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
