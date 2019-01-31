package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Rentable {
    private String name;
    private String description;
    private BufferedImage image;

    public Rentable(String name, String description, BufferedImage image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BufferedImage getImage() {
        return image;
    }
}
