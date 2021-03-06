package it.uniroma2.ispw.fersa.Boundary.Enum;

public enum TitleOfWindows {
    CREATEPAYMENTCLAIM ("Crea segnalazione pagamento - Termina contratto - FERSA"),
    LOGIN ("Login - Termina contratto - FERSA"),
    USERPANEL("Pannello utente - Termina contratto - FERSA"),
    SEEPAYMENTCLAIM("Visualizza segnalazioni pagamento - Termina contratto - FERSA")
    ;

    private final String toString;

    TitleOfWindows(String string) {
        this.toString = string;
    }

    public String getString() {
        return this.toString;
    }

}

