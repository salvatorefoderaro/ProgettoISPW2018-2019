package it.uniroma2.ispw.fersa.rentingManagement;

import java.time.LocalDate;
import java.util.*;

/**
 * 
 */
public class RequestForm {

    /**
     * Default constructor
     */
    public RequestForm(Tenant tenant, Renter renter, Rentable rentable) {
        this.tenant = tenant;
        this.renter = renter;
        this.rentable = rentable;

    }

    /**
     * 
     */
    private LocalDate begin;

    /**
     * 
     */
    private LocalDate end;

    private Tenant tenant;

    private Renter renter;

    private Rentable rentable;

    private ContractType contractType;





    /**
     * @param contractType
     */
    public void setContratcType(ContractType contractType) {
        this.contractType = contractType;
    }

    /**
     * @param begin
     * @param end
     */
    public void setPeriod(LocalDate begin, LocalDate end) {
    }

    /**
     * @param service
     */
    public void addService(Service service) {
        // TODO implement here
    }

    /**
     * @param service
     */
    public void deleteService(Service service) {
        // TODO implement here
    }

    /**
     * @return
     */
    public List<Service> getAllServices() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Price getTotal() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public IntervalDate getPeriod() {
        // TODO implement here
        return null;
    }

}