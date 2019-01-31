package Bean;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Contract", schema = "RentingManagement", catalog = "")
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

    @Id
    @Column(name = "contractID")
    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "type")
    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    @Basic
    @Column(name = "state")
    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    @Basic
    @Column(name = "tenantNickname")
    public String getTenantNickname() {
        return tenantNickname;
    }

    public void setTenantNickname(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    @Basic
    @Column(name = "renterNickname")
    public String getRenterNickname() {
        return renterNickname;
    }

    public void setRenterNickname(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    @Basic
    @Column(name = "creationDate")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "stipulationDate")
    public Date getStipulationDate() {
        return stipulationDate;
    }

    public void setStipulationDate(Date stipulationDate) {
        this.stipulationDate = stipulationDate;
    }

    @Basic
    @Column(name = "startDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "endDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "tenantName")
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    @Basic
    @Column(name = "tenantSurname")
    public String getTenantSurname() {
        return tenantSurname;
    }

    public void setTenantSurname(String tenantSurname) {
        this.tenantSurname = tenantSurname;
    }

    @Basic
    @Column(name = "tenantCF")
    public String getTenantCf() {
        return tenantCf;
    }

    public void setTenantCf(String tenantCf) {
        this.tenantCf = tenantCf;
    }

    @Basic
    @Column(name = "tenantAddress")
    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    @Basic
    @Column(name = "renterName")
    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    @Basic
    @Column(name = "renterSurname")
    public String getRenterSurname() {
        return renterSurname;
    }

    public void setRenterSurname(String renterSurname) {
        this.renterSurname = renterSurname;
    }

    @Basic
    @Column(name = "renterCF")
    public String getRenterCf() {
        return renterCf;
    }

    public void setRenterCf(String renterCf) {
        this.renterCf = renterCf;
    }

    @Basic
    @Column(name = "renterAddress")
    public String getRenterAddress() {
        return renterAddress;
    }

    public void setRenterAddress(String renterAddress) {
        this.renterAddress = renterAddress;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "deposit")
    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    @Basic
    @Column(name = "claimReported")
    public byte getClaimReported() {
        return claimReported;
    }

    public void setClaimReported(byte claimReported) {
        this.claimReported = claimReported;
    }

    @Basic
    @Column(name = "serviceList")
    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    @Basic
    @Column(name = "grossPrice")
    public int getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(int grossPrice) {
        this.grossPrice = grossPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractEntity that = (ContractEntity) o;
        return contractId == that.contractId &&
                price == that.price &&
                deposit == that.deposit &&
                claimReported == that.claimReported &&
                grossPrice == that.grossPrice &&
                Objects.equals(type, that.type) &&
                Objects.equals(state, that.state) &&
                Objects.equals(tenantNickname, that.tenantNickname) &&
                Objects.equals(renterNickname, that.renterNickname) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(stipulationDate, that.stipulationDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(tenantName, that.tenantName) &&
                Objects.equals(tenantSurname, that.tenantSurname) &&
                Objects.equals(tenantCf, that.tenantCf) &&
                Objects.equals(tenantAddress, that.tenantAddress) &&
                Objects.equals(renterName, that.renterName) &&
                Objects.equals(renterSurname, that.renterSurname) &&
                Objects.equals(renterCf, that.renterCf) &&
                Objects.equals(renterAddress, that.renterAddress) &&
                Objects.equals(serviceList, that.serviceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractId, type, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCf, tenantAddress, renterName, renterSurname, renterCf, renterAddress, price, deposit, claimReported, serviceList, grossPrice);
    }
}