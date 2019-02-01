package Bean;

import java.time.LocalDate;

public class availabilityPeriodBean {
    private LocalDate startDate;
    private LocalDate endDate;

    public availabilityPeriodBean(){}

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public LocalDate getStartDate(){ return this.startDate; }
    public LocalDate getEndDate(){ return this.endDate; }
}
