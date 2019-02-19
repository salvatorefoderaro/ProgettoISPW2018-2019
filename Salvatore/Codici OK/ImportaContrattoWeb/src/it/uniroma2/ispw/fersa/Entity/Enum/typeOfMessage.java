package it.uniroma2.ispw.fersa.Entity.Enum;

public enum typeOfMessage {
    DBERROR ("Errore nella comunicazione con il database!"),
    TRANSATIONERROR ("Errore nell'esecuzione della richiesta!"),
    NOTLOGGED("Effettua l'accesso prima di continuare!"),
    SUCCESSOPERATION("Operazione completata con successo!"),
    WRONGDATEINTERVAL("Inserisci un intervallo di date corretto!"),
    DBCONFIGERROR("Errore nel caricamento dei file relativi alla configurazione del database!")
    ;

    private final String toString;

    typeOfMessage(String string) {
        this.toString = string;
    }

    public String getString() {
        return this.toString;
    }

    public static typeOfMessage getType(String value) {
        for (typeOfMessage type : values()) {
            if (type.getString().equals(value)){
                return type;
            }
        }
        return null;
    }
}

