package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class CanceledRequestException extends Exception {
    public CanceledRequestException(){
        super("Errore nella risposta alla richiesta");
    }

    @Override
    public String toString(){
        return this.getMessage() + ": il locatario ha annullato la richiesta.";
    }

}
