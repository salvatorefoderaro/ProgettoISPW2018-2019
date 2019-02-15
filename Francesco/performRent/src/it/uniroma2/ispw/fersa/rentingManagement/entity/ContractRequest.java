package it.uniroma2.ispw.fersa.rentingManagement.entity;

import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContractRequest {

    private ContractRequestId requestId;
    private String renterNickname;
    private String tenantNickname;
    private int propertyId;
    private ContractType contractType;
    private RequestStateEnum state;
    private LocalDate creationDate;
    private DateRange dateRange;
    private int propertyPrice;
    private int deposit;
    private List<Service> services = new ArrayList<>();
    private String declineMotivation;

    public ContractRequest (String renterNickname, String tenantNickname, int propertyId, int propertyPrice, int deposit) {
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.propertyId = propertyId;
        this.propertyPrice = propertyPrice;
        this.deposit = deposit;
    }

    public ContractRequest (ContractRequestId requestId, String renterNickname, String tenantNickname, RequestStateEnum state, LocalDate creationDate, LocalDate beginDate, LocalDate endDate, int propertyPrice, int deposit, String declineMotivation) {
        this.requestId = requestId;
        this.renterNickname = renterNickname;
        this.tenantNickname = tenantNickname;
        this.state = state;
        this.creationDate = creationDate;
        this.dateRange = new DateRange(beginDate, endDate);
        this.propertyPrice = propertyPrice;
        this.deposit=deposit;
        this.declineMotivation = declineMotivation;
    }

    public void setContractType(ContractType contractType) throws ContractPeriodException {
        if (this.dateRange != null) {
            this.contractType = contractType;
            DateRange period = this.dateRange;
            this.dateRange = null;
            this.insertPeriod(period);
            return;
        }
        this.contractType = contractType;
    }

    public void insertPeriod(DateRange dateRange) throws ContractPeriodException {
        if (this.contractType != null && this.contractType.checkPeriod(dateRange)) throw new ContractPeriodException();
        this.dateRange = dateRange;
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

    public int getPropertyId() {
        return this.propertyId;
    }

    public LocalDate getCreationDate(){
        return this.creationDate;
    }

    public LocalDate getStartDate() {
        return this.dateRange.getBeginDate();
    }

    public LocalDate getEndDate() {
        return this.dateRange.getEndDate();
    }

    public int getPropertyPrice() {
        return propertyPrice;
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

        total += (int) ((this.dateRange.getNumMonths() * this.propertyPrice));

        for (int i = 0; i < this.services.size(); i++) {
            total += (int) ((this.dateRange.getNumMonths() * this.services.get(i).getPrice()));
        }

        return total;
    }
    public String getDeclineMotivation(){
        return this.declineMotivation;
    }

    public boolean check() {
        return this.contractType != null && this.dateRange != null;
    }

    public ContractRequestId getRequestId() {
        return requestId;
    }


}
