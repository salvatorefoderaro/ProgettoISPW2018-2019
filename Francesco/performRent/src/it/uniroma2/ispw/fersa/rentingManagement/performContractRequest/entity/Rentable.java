package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity;


import java.awt.*;

public class Rentable {
    private String name;
    private String description;
    private Image image;

    public Rentable(String name, String description, Image image) {
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

    public Image getImage() {
        return image;
    }
}
