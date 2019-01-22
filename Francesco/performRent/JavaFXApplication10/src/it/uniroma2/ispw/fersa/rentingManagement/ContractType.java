package it.uniroma2.ispw.fersa.rentingManagement;

/**
 * 
 */
public class ContractType {

    /**
     * Default constructor
     */
    public ContractType(String name, String description, int minDuration, int maxDuration) {
        this.name = name;
        this.description = description;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private int minDuration;

    /**
     * 
     */
    private int maxDuration;


    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return
     */
    public int getMinDuration() {
        return minDuration;
    }

    /**
     * @return
     */
    public int getMaxDuration() {
        return maxDuration;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }
}