package Entity;

import Bean.availabilityPeriodBean;

import java.time.LocalDate;

public class availabilityPeriod {

    private LocalDate startDate;
    private LocalDate endDate;

    public availabilityPeriod(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isAvaiableOnPeriod(LocalDate startPeriod, LocalDate endPeriod) {

        return (startPeriod.isAfter(this.startDate) || startPeriod.equals(this.startDate)) && (endPeriod.isBefore(this.endDate) || endPeriod.equals(this.endDate));
    }

    public boolean isEqual(LocalDate startPeriod, LocalDate endPeriod){
        if (startPeriod.equals(this.startDate) && endPeriod.equals(this.endDate)){
            return true;
        } else {
            return false;
        }
    }

    public availabilityPeriodBean makeBean(){
        availabilityPeriodBean bean = new availabilityPeriodBean();
        bean.setStartDate(this.startDate);
        bean.setEndDate(this.endDate);
        return bean;
    }
}
