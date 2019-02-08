package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;

import java.time.LocalDate;

public class ContractBean {

    private ContractRequestId contractRequestId;

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

    public ContractBean(ContractRequestId contractRequestId, String tenantName, String tenantSurname, String tenantCF,
                        LocalDate tenantDateOfBirth, String tenantCityOfBirth,String tenantAddress, String renterName, String renterSurname,
                        String renterCF, String renterAddress) {
        this.contractRequestId = contractRequestId;
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
    }

    public ContractRequestId getContractRequestId() {
        return contractRequestId;
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
}
