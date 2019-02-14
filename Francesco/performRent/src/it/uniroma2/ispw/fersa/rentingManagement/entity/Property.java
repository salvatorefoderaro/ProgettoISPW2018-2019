package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.awt.image.BufferedImage;

public class Property {
    private int rentableId;
    private PropertyTypeEnum type;
    private String name;
    private String description;
    private BufferedImage image;

    public Property(int rentableId, PropertyTypeEnum type , String name, String description, BufferedImage image) {
        this.rentableId = rentableId;
        this.type = type;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public int getRentableId() {
        return rentableId;
    }

    public String getName() {
        return name;
    }

    public PropertyTypeEnum getType(){
        return this.type;
    }

    public String getDescription() {

        return description;
    }

    public BufferedImage getImage() {

        return image;
    }
}
