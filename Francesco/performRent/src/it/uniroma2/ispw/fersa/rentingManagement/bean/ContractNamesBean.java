package it.uniroma2.ispw.fersa.rentingManagement.bean;

import java.util.List;

public class ContractNamesBean {
    private List<String> contractNames;

    public ContractNamesBean(List<String> contractNames) {
        this.contractNames = contractNames;
    }

    public List<String> getContractNames() {
        return contractNames;
    }
}
