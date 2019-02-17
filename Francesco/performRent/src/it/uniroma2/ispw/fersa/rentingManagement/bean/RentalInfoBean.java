package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.PropertyTypeEnum;

import java.awt.image.BufferedImage;
import java.util.List;

public class RentalInfoBean {
    private String title;
    private BufferedImage image;
    private PropertyTypeEnum type;
    private String rentableDescription;
    private String rentalDescription;
    private int price;
    private int deposit;
    private List<String> avaiblePeriods;


    public RentalInfoBean(String title, BufferedImage image, PropertyTypeEnum type, String rentableDescription, String rentalDescription, int price, int deposit, List<String> avaiblePeriods) {
        this.title = title;
        this.image = image;
        this.type = type;
        this.rentableDescription = rentableDescription;
        this.rentalDescription = rentalDescription;
        this.price = price;
        this.deposit = deposit;
        this.avaiblePeriods = avaiblePeriods;
    }

    public String getTitle() {
        return title;
    }

    public BufferedImage getImage() {
        return image;
    }

    public PropertyTypeEnum getType() {
        return type;
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

