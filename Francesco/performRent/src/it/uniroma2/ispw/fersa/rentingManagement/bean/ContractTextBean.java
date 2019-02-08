package it.uniroma2.ispw.fersa.rentingManagement.bean;

import java.time.LocalDate;
import java.util.List;

public class ContractTextBean {
    private String contracTypeName;
    private boolean transitory;
    private String aptAddress;
    private LocalDate startDate;
    private LocalDate endDate;
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
    private int numMonths;
    private int totalPrice;
    private int deposit;
    private List<ServiceBean> services;

    public ContractTextBean(String contracTypeName, boolean transitory, String aptAddress, LocalDate startDate, LocalDate endDate,
                            String tenantName, String tenantSurname, String tenantCF, LocalDate tenantDateOfBirth,
                            String tenantCityOfBirth, String tenantAddress, String renterName, String renterSurname,
                            String renterCF, String renterAddress, int numMonths, int totalPrice, int deposit,
                            List<ServiceBean> services) {

        this.contracTypeName = contracTypeName;
        this.transitory = transitory;
        this.aptAddress = aptAddress;
        this.startDate = startDate;
        this.endDate = endDate;
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
        this.numMonths = numMonths;
        this.totalPrice = totalPrice;
        this.deposit = deposit;
        this.services = services;
    }

    public String getContracTypeName() {
        return contracTypeName;
    }

    public boolean isTransitory(){
        return this.transitory;
    }

    public String getAptAddress() {
        return aptAddress;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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

    public String getRenterCF() {
        return renterCF;
    }

    public String getRenterAddress() {
        return renterAddress;
    }

    public int getNumMonths() {
        return numMonths;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<ServiceBean> getServices() {
        return services;
    }
}



