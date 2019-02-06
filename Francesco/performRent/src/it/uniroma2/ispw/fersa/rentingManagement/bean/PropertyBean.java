package it.uniroma2.ispw.fersa.rentingManagement.bean;

import it.uniroma2.ispw.fersa.rentingManagement.entity.RentableTypeEnum;

import java.awt.image.BufferedImage;

public class PropertyBean {
    private String aptAddress;
    private String title;
    private BufferedImage image;
    private RentableTypeEnum type;
    private String rentableDescription;

    public PropertyBean(String aptAddress, String title, BufferedImage image, RentableTypeEnum type, String rentableDescription) {
        this.aptAddress = aptAddress;
        this.title = title;
        this.image = image;
        this.type = type;
        this.rentableDescription = rentableDescription;
    }

    public String getAptAddress() {
        return aptAddress;
    }

    public String getTitle() {
        return title;
    }

    public BufferedImage getImage() {
        return image;
    }

    public RentableTypeEnum getType() {
        return type;
    }

    public String getRentableDescription() {
        return rentableDescription;
    }
}
