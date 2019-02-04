package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception;

public class PeriodException extends Exception{
    public PeriodException() {
        super("Errore nel periodo selezionato");
    }

    @Override
    public String toString(){
        return this.getMessage() + ": periodo non disponibile";
    }
}
