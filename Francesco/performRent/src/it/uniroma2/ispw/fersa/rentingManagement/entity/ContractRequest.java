package it.uniroma2.ispw.fersa.rentingManagement.entity;

import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractRequest {

    private ContractRequestId requestId;
    private String renterNickname;
    private String tenantNickname;
    private int rentableId;
    private ContractType contractType;
    private RequestStateEnum state;
    private LocalDate creationDate;
    private IntervalDate intervalDate;
    private int rentablePrice;
    private int deposit;
    private List<Service> services = new ArrayList<>();
    private String declineMotivation;

    public ContractRequest (String renterNickname, String tenantNickname, int rentableId, int rentablePrice, int deposit) {
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.rentableId = rentableId;
        this.rentablePrice = rentablePrice;
        this.deposit = deposit;
    }

    public ContractRequest (ContractRequestId requestId, String renterNickname, String tenantNickname, RequestStateEnum state, LocalDate creationDate, LocalDate beginDate, LocalDate endDate, int rentablePrice, int deposit, String declineMotivation) {
        this.requestId = requestId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.state = state;
        this.creationDate = creationDate;
        this.intervalDate = new IntervalDate(beginDate, endDate);
        this.rentablePrice = rentablePrice;
        this.deposit=deposit;
        this.declineMotivation = declineMotivation;
    }

    public void setContractType(ContractType contractType) throws ContractPeriodException {
        if (this.intervalDate != null) {
            this.contractType = contractType;
            IntervalDate period = this.intervalDate;
            this.intervalDate = null;
            this.insertPeriod(period);
            return;
        }
        this.contractType = contractType;
    }

    public void insertPeriod(IntervalDate intervalDate) throws ContractPeriodException {
        if (this.contractType != null && this.contractType.checkPeriod(intervalDate)) throw new ContractPeriodException();
        this.intervalDate = intervalDate;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getContractName() {
        return this.contractType.getName();
    }

    public RequestStateEnum getState() {
        return state;
    }

    public String getRenterNickname() {
        return renterNickname;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public int getRentableId() {
        return this.rentableId;
    }

    public LocalDate getCreationDate(){
        return this.creationDate;
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

    public int getContractTypeId() {
        return this.contractType.getContractTypeId();
    }

    public ContractRequestId getContractRequestId() {
        return this.requestId;
    }

    public int getTotal() {
        int total = 0;

        total += (int) (this.intervalDate.getNumMonths() * this.rentablePrice);

        for (int i = 0; i < this.services.size(); i++) {
            total += (int) (this.intervalDate.getNumMonths() * this.services.get(i).getPrice());
        }

        return total;
    }
    public String getDeclineMotivation(){
        return this.declineMotivation;
    }

    public boolean check() {
        return this.contractType != null && this.intervalDate != null;
    }

    public ContractRequestId getRequestId() {
        return requestId;
    }


}
