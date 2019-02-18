package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class UserIdException extends Exception {
    public UserIdException() {
        super("Errore: inesistenza di un utente con il nickname scelto");
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
