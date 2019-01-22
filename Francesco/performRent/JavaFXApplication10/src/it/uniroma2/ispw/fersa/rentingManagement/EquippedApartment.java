package it.uniroma2.ispw.fersa.rentingManagement;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * 
 */
public class EquippedApartment {

    /**
     * Default constructor
     */
    public EquippedApartment() {
    }

    /**
     * 
     */
    private EquippedApartmentId eApartmentId;

    /**
     * 
     */
    private List<Image> images;

    /**
     * 
     */
    private String description;











    /**
     * @param begin
     * @param end
     * @return
     */
    public List<Tenant> getTenantsByPeriod(LocalDate begin, LocalDate end) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Renter getRenter() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public List<Service> getServices() {
        // TODO implement here
        return null;
    }

}