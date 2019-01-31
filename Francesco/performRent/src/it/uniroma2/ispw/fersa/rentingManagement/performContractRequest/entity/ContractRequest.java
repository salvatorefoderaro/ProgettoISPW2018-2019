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
    private IntervalDate intervalDate;
    private int rentablePrice;
    private int deposit;
    private List<Service> services = new ArrayList<>();

    public ContractRequest (String renterNickname, String tenantNickname, Rentable rentable, int rentablePrice, int deposit) {
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.rentable = rentable;
        this.rentablePrice = rentablePrice;
        this.deposit = deposit;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public void insertPeriod(IntervalDate intervalDate) throws PeriodException {
        if (this.contractType.checkPeriod(intervalDate)) throw new PeriodException();
        this.intervalDate = intervalDate;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getContractName() {
        return this.contractType.getName();
    }

    public String getRentableName() {
        return this.rentable.getName();
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public LocalDate getStartDate() {
        return this.intervalDate.getBeginDate();
    }

    public LocalDate getEndDate() {
        return this.intervalDate.getEndDate();
    }

    public int getRentablePrice() {
        return rentablePrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<Service> getServices() {
        return services;
    }

    public int getContractId() {
        return this.contractType.getContractTypeId();
    }

    public int getTotal() {
        int total = 0;

        total += (int) (this.intervalDate.getNumMonths() * this.rentablePrice);

        for (int i = 0; i < this.services.size(); i++) {
            total += (int) (this.intervalDate.getNumMonths() * this.services.get(i).getPrice());
        }

        return total;
    }
}
