package it.uniroma2.ispw.fersa.rentingManagement.entity;

public class ContractType {
    private  int contractTypeId;
    private String name;
    private String description;
    private boolean transitory;
    private int minDuration;
    private int maxDuration;

    public ContractType(int contractTypeId, String name, String description, boolean transitory,int minDuration, int maxDuration) {
        this.contractTypeId = contractTypeId;
        this.name = name;
        this.description = description;
        this.transitory = transitory;
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

    public boolean isTransitory(){
        return this.transitory;
    }

    public boolean checkPeriod(DateRange dateRange) {
        int period = (int) dateRange.getNumMonths();

        return period < minDuration | period > maxDuration;
    }
}