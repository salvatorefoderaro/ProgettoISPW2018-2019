package it.uniroma2.ispw.fersa.rentingManagement;

import java.time.LocalDate;
import java.time.Period;

public class IntervalDate {

    public IntervalDate(LocalDate begin, LocalDate end) {
        this.begin = begin;
        this.end = end;
    }

    private LocalDate begin, end;

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getNumMonths() {
       return (int) Period.between(this.begin, this.end.plusDays(1)).toTotalMonths(); //Period.between(begin(inclusive), end(exclusive))
    }
}

