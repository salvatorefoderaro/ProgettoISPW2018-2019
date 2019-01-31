package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;


public class ContractTypeBean {
    private int contractTypeId;
    private String name;
    private String description;
    private int minDuration;
    private int maxDuration;

    public ContractTypeBean(int contractTypeId, String name, String description, int minDuration, int maxDuration) {
        this.contractTypeId = contractTypeId;
        this.name = name;
        this.description = description;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public int getContractTypeId() {
        return contractTypeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

}
