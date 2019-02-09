package it.uniroma2.ispw.fersa.rentingManagement.entity;

public enum ContractStateEnum {
    ACTIVE ("ATTIVO", "ATTIVO"),
    SIGNATURE ("DA FIRMARE", "IN ATTESA DELLA FIRMA DEL LOCATARIO"),
    EXPIRED("CONCLUSO", "CONCLUSO"),
    CANCELED("ANNULLATO", "ANNULLATO");

    private String tenantState;
    private String renterState;

    private ContractStateEnum(String tenantState, String renterState) {
        this.tenantState = tenantState;
        this.renterState = renterState;
    }

    public String getTenantState() {
        return this.tenantState;
    }

    public String getRenterState() {
        return this.renterState;
    }
}
