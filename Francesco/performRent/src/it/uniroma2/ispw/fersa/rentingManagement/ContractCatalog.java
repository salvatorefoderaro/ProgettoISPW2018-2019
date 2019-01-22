package it.uniroma2.ispw.fersa.rentingManagement;
import java.util.*;

/**
 * 
 */
public class ContractCatalog {

    /**
     * Default constructor
     */
    protected ContractCatalog() {
        this.contracts = new ArrayList<>();
        generatesContracts();
    }

    private List<ContractType> contracts;

    private static ContractCatalog contractCatalog;

    /**
     * @return
     */
    public List<String> getAllContractTypes() {
        List<String> contractnames = new ArrayList<>();

        this.contracts.forEach(contractType -> contractnames.add(contractType.getName()));

        return contractnames;
    }

    /**
     * @param name
     * @return
     */
    public ContractType getContract(String name) {

        for (int i = 0; i < this.contracts.size(); i++) {
            if (this.contracts.get(i).getName().equals(name)) return this.contracts.get(i);
        }
        return null;

    }


    //MOCK
    private void addContratc(ContractType contractType) {
        this.contracts.add(contractType);
    }

    /**
     * @return
     */
    public static synchronized ContractCatalog getContractCatalogIstance() {
        if (contractCatalog == null) {
            return contractCatalog = new ContractCatalog();
        }
        else {
            return contractCatalog;
        }
    }

    private void generatesContracts() {
        for (int i = 0; i < 10; i++) {
            this.addContratc(new ContractType("Contract " + (i + 1), "Description of contract " + (i + 1), i, 2 * (i + 1)));
        }
    }

}