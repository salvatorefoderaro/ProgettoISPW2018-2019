package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class CanceledContractException extends Exception {
    public CanceledContractException() {
        super("Errore nella firma del contratto");
    }

    @Override
    public String toString() {
        return getMessage() + ": il contratto è stato annullato";
    }
}
