package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.time.LocalDate;
import java.util.List;

public class RentalFeatures {

    private int rentalFeaturesId;
    private String description;
    private int price;
    private int deposit;
    private List<DateRange> availability;


    public RentalFeatures(int rentalFeaturesId, String description, int price, int deposit, List<DateRange> availability) {
        this.rentalFeaturesId = rentalFeaturesId;
        this.description = description;
        this.price = price;
        this.deposit = deposit;
        this.availability = availability;

    }

    public int getRentalFeaturesId() {
        return rentalFeaturesId;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<DateRange> getAvailability() {
        return availability;
    }

    public boolean checkPeriod(LocalDate start, LocalDate end) {
        for (int i = 0; i<this.availability.size(); i++) {
            DateRange dateRange = this.availability.get(i);
            if ((dateRange.getStartDate().isBefore(start) | dateRange.getStartDate().isEqual(start)) &
                    (dateRange.getEndDate().isAfter(end) | dateRange.getEndDate().isEqual(end))) return true;
        }

        return false;
    }
}
