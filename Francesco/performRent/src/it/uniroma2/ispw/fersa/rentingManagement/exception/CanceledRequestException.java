package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class CanceledRequestException extends Exception {
    public CanceledRequestException () {
        super("Errore nell'annullamento della richiesta");
    }

    @Override
    public String toString() {
        return this.getMessage() + ": la richiesta è ststa accettata";
    }
}