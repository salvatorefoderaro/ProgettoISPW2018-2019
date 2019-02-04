package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class ContractPeriodException extends Exception {
    public ContractPeriodException() {
        super("Problema con il periodo");
    }

    @Override
    public String toString() {
        return this.getMessage() + ": durata del periodo non soddisfa i requisiti del contratto!";
    }
}
