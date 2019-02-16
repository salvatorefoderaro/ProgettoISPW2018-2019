package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateRange {
    private LocalDate startDate;

    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getNumMonths() {
        return  Period.between(this.startDate, this.endDate.plusDays(1)).toTotalMonths(); //Period.between(begin(inclusive), end(exclusive))
    }

    @Override
    public String toString() {
        return "[" + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "]";
    }
}
