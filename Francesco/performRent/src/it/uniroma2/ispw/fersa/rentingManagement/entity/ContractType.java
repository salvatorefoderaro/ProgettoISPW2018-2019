package it.uniroma2.ispw.fersa.rentingManagement.entity;

public class ContractType {
    private  int contractTypeId;
    private String name;
    private String description;
    private int minDuration;
    private int maxDuration;

    public ContractType(int contractTypeId, String name, String description, int minDuration, int maxDuration) {
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

    public boolean checkPeriod(IntervalDate intervalDate) {
        int period = (int) intervalDate.getNumMonths();

        return period < minDuration | period > maxDuration;
    }
}