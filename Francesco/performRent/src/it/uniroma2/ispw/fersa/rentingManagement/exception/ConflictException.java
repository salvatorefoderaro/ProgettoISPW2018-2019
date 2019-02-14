package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class ConflictException extends Exception {
    public ConflictException() {
        super("Errore nella firma del contratto");
    }

    @Override
    public String toString() {
        return this.getMessage() + ": presenza di una procedura di stipulazione contratto il cui periodo entra " +
        "in conflitto con quello di questa procedura";
    }


}
