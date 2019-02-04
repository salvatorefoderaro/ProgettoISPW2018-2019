package it.uniroma2.ispw.fersa.rentingManagement.exception;

public class ConfigFileException extends Exception {
    public ConfigFileException() {super("Errore nel caricamento del file di configurazione");}

    @Override
    public String toString() {
        return this.getMessage() + ": " + System.getProperty("user.dir") + "/config/db.config";
    }
}
