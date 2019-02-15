package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class AnsweredRequestException extends Exception {
    public AnsweredRequestException() {
        super("Errore nell'annullamento della richiesta");
    }

    @Override
    public String toString() {
        return this.getMessage() + ": il locatore ha risposto alla richiesta";
    }
}
