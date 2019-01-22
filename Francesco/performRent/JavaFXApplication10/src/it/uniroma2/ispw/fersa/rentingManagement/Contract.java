package it.uniroma2.ispw.fersa.rentingManagement;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class Contract {

    /**
     * Default constructor
     */
    public Contract() {
    }

    /**
     * 
     */
    private ContractId contractId;

    /**
     * 
     */
    private Date stipulationDate;

    /**
     * 
     */
    private Date expirationDate;

    /**
     * 
     */
    private Price price;

    /**
     * 
     */
    private TermsOfPayement termsOfPayement;







    /**
     * @param begin
     * @param end
     * @return
     */
    public boolean comparePeriod(LocalDate begin, LocalDate end) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public Tenant getTenant() {
        // TODO implement here
        return null;
    }

}