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
    }

    private static ContractCatalog contractCatalog;

    /**
     * @return
     */
    public List<String> getAllContractTypes() {
        // TODO implement here
        return null;
    }

    /**
     * @param name
     * @return
     */
    public ContractType getContract(String name) {
        // TODO implement here
        return null;
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