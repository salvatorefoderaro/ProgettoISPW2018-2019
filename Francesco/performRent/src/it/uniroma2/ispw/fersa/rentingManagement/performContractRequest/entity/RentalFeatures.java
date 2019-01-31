package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

public class RentalFeatures {

    private int rentalFeaturesId;
    private String description;
    private int price;
    private int deposit;
    private List<IntervalDate> avaibility;


    public RentalFeatures(int rentalFeaturesId, String description, int price, int deposit, List<IntervalDate> avaibility) {
        this.rentalFeaturesId = rentalFeaturesId;
        this.description = description;
        this.price = price;
        this.deposit = deposit;
        this.avaibility = avaibility;

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

    public List<IntervalDate> getAvaibility() {
        return avaibility;
    }

    public boolean checkPeriod(LocalDate begin, LocalDate end) {
        for (int i=0; i<this.avaibility.size(); i++) {
            IntervalDate intervalDate = this.avaibility.get(i);
            if ((intervalDate.getBeginDate().isBefore(begin) | intervalDate.getBeginDate().isEqual(begin)) &
                    (intervalDate.getEndDate().isAfter(end) | intervalDate.getEndDate().isEqual(end))) return true;
        }

        return false;
    }
}
