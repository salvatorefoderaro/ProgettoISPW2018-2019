package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.IntervalDate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.List;

public class RentableInfoBean {
    private String title;
    private Image image;
    private String rentableDescription;
    private String rentalDescription;
    private int price;
    private int deposit;
    private List<String> avaiblePeriods;


    public RentableInfoBean(String title, Image image,String rentableDescription, String rentalDescription, int price, int deposit, List<String> avaiblePeriods) {
        this.title = title;
        this.image = image;
        this.rentableDescription = rentableDescription;
        this.rentalDescription = rentalDescription;
        this.price = price;
        this.deposit = deposit;
        this.avaiblePeriods = avaiblePeriods;
    }

    public String getTitle() {
        return title;
    }

    public Image getImage() {
        return image;
    }

    public String getRentableDescription() {
        return rentableDescription;
    }

    public String getRentalDescription() {
        return rentalDescription;
    }

    public int getPrice() {
        return price;
    }

    public int getDeposit() {
        return deposit;
    }

    public List<String> getAvaiblePeriods() {
        return avaiblePeriods;
    }
}

