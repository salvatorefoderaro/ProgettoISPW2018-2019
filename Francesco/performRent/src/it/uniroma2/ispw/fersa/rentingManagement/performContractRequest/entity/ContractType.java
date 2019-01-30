package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

import java.time.LocalDate;
import java.time.Period;

public class ContractType {
    private String name;
    private String description;
    private int minDuration;
    private int maxDuration;

    public ContractType(String name, String description, int minDuration, int maxDuration) {
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

    public boolean checkPeriod(LocalDate start, LocalDate end) {
        int period = (int) Period.between(start, end.plusDays(1)).toTotalMonths();

        return period < minDuration | period > maxDuration;
    }
}