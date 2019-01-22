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

    public void addContratc(ContractType contractType) {
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

}