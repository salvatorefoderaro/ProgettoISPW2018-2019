package it.uniroma2.ispw.fersa.rentingManagement.entity;

public enum RequestStateEnum {
    INSERTED("INSERITA", "NON GESTITA"),
    APPROVED("APPROVATA", "APPROVATA"),
    REFUSUED("RIFIUTATA", "RIFIUTATA"),
    CANCELED("ANNULLATA", "ANNULLATA");

    private String tenantState;
    private String renterState;

    private RequestStateEnum(String tenantState, String renterState) {
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
