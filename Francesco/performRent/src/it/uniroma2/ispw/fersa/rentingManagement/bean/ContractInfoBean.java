package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractStateEnum;
import java.time.LocalDate;
import java.util.List;

public class ContractInfoBean {
    private String contractName;
    private String nickname;
    private String name;
    private String surname;
    private String CF;
    private LocalDate creationDate;
    private LocalDate stipulationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rentablePrice;
    private int deposit;
    private List<ServiceBean> services;
    private int total;
    private ContractStateEnum state;


    public ContractInfoBean(String contractName, String nickname, String name, String surname,String CF,LocalDate creationDate, LocalDate stipulationDate,LocalDate startDate, LocalDate endDate, int rentablePrice, int deposit, List<ServiceBean> services, int total, ContractStateEnum state) {
        this.contractName = contractName;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.CF = CF;
        this.creationDate = creationDate;
        this.stipulationDate = stipulationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentablePrice = rentablePrice;
        this.deposit = deposit;
        this.services = services;
        this.total = total;
        this.state = state;
    }

    public String getContractName() {
        return contractName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCF() {
        return CF;
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

    public int getRentablePrice() {
        return rentablePrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<ServiceBean> getServices() {
        return services;
    }

    public int getTotal() {
        return this.total;
    }

    public ContractStateEnum getState() {
        return state;
    }

}
