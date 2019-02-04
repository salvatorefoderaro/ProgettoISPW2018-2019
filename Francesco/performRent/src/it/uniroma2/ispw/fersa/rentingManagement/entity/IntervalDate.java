package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class IntervalDate {
    private LocalDate beginDate;

    private LocalDate endDate;

    public IntervalDate(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getNumMonths() {
        return  Period.between(this.beginDate, this.endDate.plusDays(1)).toTotalMonths(); //Period.between(begin(inclusive), end(exclusive))
    }

    @Override
    public String toString() {
        return "[" + beginDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "]";
    }
}
