package it.uniroma2.ispw.fersa.rentingManagement.exception;



public class UserTypeException extends Exception {
    public UserTypeException() {
        super("Errore: l'utente non è associato al tipo selezionato");
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
