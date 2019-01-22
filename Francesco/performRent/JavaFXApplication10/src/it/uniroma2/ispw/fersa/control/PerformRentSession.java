package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.boundary.performRentForm.Controller;
import it.uniroma2.ispw.fersa.rentingManagement.*;


import java.time.LocalDate;

/**
 * 
 */
public class PerformRentSession {

    /**
     * Default constructor
     */
    public PerformRentSession(Tenant tenant, Rentable rentable, EquippedApartment eApartment) {
        this.tenant = tenant;
        this.rentable = rentable;
        this.eApartment = eApartment;

        this.contractCatalog = ContractCatalog.getContractCatalogIstance();

        this.requestForm = new RequestForm(tenant, ((EquippedApartment) this.eApartment).getRenter(), rentable);
    }

    public PerformRentSession(Controller controller, Rentable rentable) {
        this.rentable = rentable;
        this.controller = controller;

    }


    private Tenant tenant;

    private Rentable rentable;

    private EquippedApartment eApartment;

    private RequestForm requestForm;

    private ContractCatalog contractCatalog;

    private Controller controller;




    /**
     * @param name
     */
    public void selectContract(String name) {
        ContractType contractType;

        contractType = contractCatalog.getContract(name);
        requestForm.setContratcType(contractType);
    }

    /**
     * @param begin
     * @param end
     */
    public void enterPeriod(LocalDate begin, LocalDate end) throws PeriodException {
        try {
            requestForm.setPeriod(begin, end);
        }
        catch (PeriodException exc) {

        }
    }


    /**
     * 
     */
    public void endRequest() {
        // TODO implement here
    }

    /**
     * 
     */
    public void sendRequest() {
        // TODO implement here
    }

}