package Bean;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

public class ContractEntity {
    private int contractId;
    private Object type;
    private Object state;
    private String tenantNickname;
    private String renterNickname;
    private Date creationDate;
    private Date stipulationDate;
    private Date startDate;
    private Date endDate;
    private String tenantName;
    private String tenantSurname;
    private String tenantCf;
    private String tenantAddress;
    private String renterName;
    private String renterSurname;
    private String renterCf;
    private String renterAddress;
    private int price;
    private int deposit;
    private byte claimReported;
    private String serviceList;
    private int grossPrice;


    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }


    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }


    public String getRenterNickname() {
        return renterNickname;
    }

    public void setRenterNickname(String renterNickname) {
        this.renterNickname = renterNickname;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getStipulationDate() {
        return stipulationDate;
    }

    public void setStipulationDate(Date stipulationDate) {
        this.stipulationDate = stipulationDate;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }


    public String getTenantSurname() {
        return tenantSurname;
    }

    public void setTenantSurname(String tenantSurname) {
        this.tenantSurname = tenantSurname;
    }

    public String getTenantCf() {
        return tenantCf;
    }

    public void setTenantCf(String tenantCf) {
        this.tenantCf = tenantCf;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterSurname() {
        return renterSurname;
    }

    public void setRenterSurname(String renterSurname) {
        this.renterSurname = renterSurname;
    }

    public String getRenterCf() {
        return renterCf;
    }

    public void setRenterCf(String renterCf) {
        this.renterCf = renterCf;
    }

    public String getRenterAddress() {
        return renterAddress;
    }

    public void setRenterAddress(String renterAddress) {
        this.renterAddress = renterAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public byte getClaimReported() { return claimReported; }

    public void setClaimReported(byte claimReported) {
        this.claimReported = claimReported;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public int getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(int grossPrice) {
        this.grossPrice = grossPrice;
    }


}
