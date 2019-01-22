package it.uniroma2.ispw.fersa.rentingManagement;
import java.awt.*;
import java.time.LocalDate;

/**
 * 
 */
public class Rentable {

    /**
     * Default constructor
     */
    public Rentable() {
    }

    /**
     * 
     */
    private Image image;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Price price;

    private AvaibilityCalendar avaibilityCalendar;



    /**
     * @param begin
     * @param end
     * @return
     */
    public boolean isAvaible(LocalDate begin, LocalDate end) {
        // TODO implement here
        return false;
    }

}