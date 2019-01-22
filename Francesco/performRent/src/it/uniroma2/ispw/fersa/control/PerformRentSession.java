package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.boundary.performRentForm.Controller;
import it.uniroma2.ispw.fersa.rentingManagement.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTypeBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractTypeIdBean;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class PerformRentSession {

    /**
     * Default constructor
     */
    public PerformRentSession(Tenant tenant, Rentable rentable, EquippedApartment eApartment, Controller controller) {
        this.tenant = tenant;
        this.rentable = rentable;
        this.eApartment = eApartment;

        this.contractCatalog = ContractCatalog.getContractCatalogIstance();

        this.controller = controller;
        this.requestForm = new RequestForm(tenant, null, rentable); //TODO Modificare il renter quando implementato
    }

    public PerformRentSession(Controller controller) {
        this.controller = controller;

        this.contractCatalog = ContractCatalog.getContractCatalogIstance();

        this.requestForm = new RequestForm(null, null, null);

        List<ContractTypeIdBean> contracts = getAllContratcs();

        controller.setContratList(contracts);

    }


    private Tenant tenant;

    private Rentable rentable;

    private EquippedApartment eApartment;

    private RequestForm requestForm;

    private ContractCatalog contractCatalog;

    private Controller controller;


    public List<ContractTypeIdBean> getAllContratcs () {
        List<String> contractsNames = contractCatalog.getAllContractTypes();
        List<ContractTypeIdBean> contractsId = new ArrayList<>();

        contractsNames.forEach(contractName -> contractsId.add(new ContractTypeIdBean(contractName)));

       return contractsId;

    }

    /**
     * @param name
     */
    public void selectContract(ContractTypeIdBean contractId) {
        ContractType contractType;

        contractType = contractCatalog.getContract(contractId.getName());
        requestForm.setContratcType(contractType);
        controller.setContractDescription(new ContractTypeBean(contractType.getName(), contractType.getDescription(), contractType.getMinDuration(), contractType.getMaxDuration()));
    }

    /**
     * @param begin
     * @param end
     */
    public void enterPeriod(LocalDate begin, LocalDate end) {
        try {
            requestForm.setPeriod(begin, end);
        }
        catch (PeriodException exc) {
            controller.setPeriodError("Il periodo selezionato non è conforme alle specifiche del contratto selezionato");
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