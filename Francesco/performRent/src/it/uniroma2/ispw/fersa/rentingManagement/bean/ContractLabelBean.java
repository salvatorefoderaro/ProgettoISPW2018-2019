package it.uniroma2.ispw.fersa.rentingManagement.bean;


import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractStateEnum;

import java.time.LocalDate;

public class ContractLabelBean {

    private int contractId;

    private String nickname;

    private LocalDate creationDate;

    private LocalDate stipulationDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private int totalPrice;

    private ContractStateEnum state;

    public ContractLabelBean(int contractId, String nickname, LocalDate creationDate, LocalDate stipulationDate,
                             LocalDate startDate, LocalDate endDate, int totalPrice, ContractStateEnum state) {
        this.contractId = contractId;
        this.nickname = nickname;
        this.creationDate = creationDate;
        this.stipulationDate = stipulationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.state = state;
    }

    public int getContractId() {
        return contractId;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getStipulationDate() {
        return stipulationDate;
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

    public ContractStateEnum getState() {
        return state;
    }
}
