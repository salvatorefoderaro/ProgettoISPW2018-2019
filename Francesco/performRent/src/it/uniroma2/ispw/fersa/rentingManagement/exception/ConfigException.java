package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class ConfigException extends Exception {
    public ConfigException() {super("Errore nel caricamento della configurazione del database");}

    @Override
    public String toString() {
        return this.getMessage() + ": parametri mancanti!";
    }
}
