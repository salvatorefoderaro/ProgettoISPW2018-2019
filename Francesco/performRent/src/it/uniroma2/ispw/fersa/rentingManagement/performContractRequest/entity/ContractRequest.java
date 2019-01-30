package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

import java.time.LocalDate;

public class ContractRequest {

    private String tenantNickname;
    private String renterNickname;
    private int deposit;
    private int rentablePrice;
    private LocalDate start;
    private LocalDate end;
    private ContractType contractType;

    public ContractRequest (String tenantNickname, String renterNickname, int price, int deposit) {
        this.tenantNickname = tenantNickname;
        this.renterNickname = renterNickname;
        this.deposit = deposit;
        this.rentablePrice = price;
    }

    public void setContractType (ContractType contractType) {
        this.contractType = contractType;
    }
}
