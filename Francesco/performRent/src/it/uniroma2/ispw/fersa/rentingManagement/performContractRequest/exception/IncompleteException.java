package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception;

public class IncompleteException extends Exception {
    public IncompleteException() {
        super("Errore: dati mancanti nella richiesta!");
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
