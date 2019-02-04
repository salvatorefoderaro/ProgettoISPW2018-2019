package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception;

public class NotFoundException extends Exception{
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
