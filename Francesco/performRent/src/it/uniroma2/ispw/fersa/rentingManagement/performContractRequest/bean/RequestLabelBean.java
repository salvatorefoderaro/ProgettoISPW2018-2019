package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RequestStateEnum;

import java.time.LocalDate;

public class RequestLabelBean {
    private int contractRequestId;
    private String tenantNickname;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPrice;
    private RequestStateEnum state;

    public RequestLabelBean(int contractRequestId, String tenantNickname, LocalDate creationDate, LocalDate startDate, LocalDate endDate, int totalPrice, RequestStateEnum state){
        this.contractRequestId = contractRequestId;
        this.tenantNickname = tenantNickname;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    public int getContractRequestId() {
        return contractRequestId;
    }

    public String getTenantNickname() {
        return tenantNickname;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public RequestStateEnum getState() {
        return state;
    }
}
