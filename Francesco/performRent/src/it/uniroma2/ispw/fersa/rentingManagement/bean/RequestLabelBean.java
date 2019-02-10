package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.RequestStateEnum;

import java.time.LocalDate;

public class RequestLabelBean {
    private int contractRequestId;
    private String nickname;
    private LocalDate creationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalPrice;
    private RequestStateEnum state;

    public RequestLabelBean(int contractRequestId, String nickname, LocalDate creationDate, LocalDate startDate, LocalDate endDate, int totalPrice, RequestStateEnum state){
        this.contractRequestId = contractRequestId;
        this.nickname = nickname;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    public int getContractRequestId() {
        return contractRequestId;
    }

    public String getNickname() {
        return nickname;
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
