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

    private int TotalPrice;

    private ContractStateEnum state;
}
