package it.uniroma2.ispw.fersa.rentingManagement.entity;

import java.awt.image.BufferedImage;

public class Rentable {
    private int rentableId;
    private RentableTypeEnum type;
    private String name;
    private String description;
    private BufferedImage image;

    public Rentable(int rentableId, RentableTypeEnum type ,String name, String description, BufferedImage image) {
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

    public RentableTypeEnum getType(){
        return this.type;
    }

    public String getDescription() {

        return description;
    }

    public BufferedImage getImage() {

        return image;
    }
}
