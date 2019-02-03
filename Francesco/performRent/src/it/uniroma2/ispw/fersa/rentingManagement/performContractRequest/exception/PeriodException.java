package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception;

public class PeriodException extends Exception {
    public PeriodException() {
        super("Problema con il periodo");
    }

    @Override
    public String toString() {
        return this.getMessage() + ": durata del periodo non soddisfa i requisiti del contratto!";
    }
}
