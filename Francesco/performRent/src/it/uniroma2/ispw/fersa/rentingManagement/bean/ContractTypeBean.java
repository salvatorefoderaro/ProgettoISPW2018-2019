package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.Contract;

public class ContractTypeBean {

    private String name;
    private String description;
    private int minDuration;
    private int maxDuration;

    public ContractTypeBean (String name, String description, int minDuration, int maxDuration) {
        this.name = name;
        this.description = description;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
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
