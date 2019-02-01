package Entity;

public enum TypeOfMessage {
    DBERROR ("Errore nella comunicazione con il database!"),
    TRANSATIONERROR ("Errore nell'esecuzione della richiesta!"),
    NOTLOGGED("Effettua l'accesso prima di continuare!"),
    SUCCESSOPERATION("Operazione completata con successo!"),
    WRONGDATEINTERVAL("Inserisci un intervallo di date corretto!");
    ;

    private final String toString;

    TypeOfMessage(String string) {
        this.toString = string;
    }

    public String getString() {
        return this.toString;
    }

    public static TypeOfMessage getType(String value) {
        for (TypeOfMessage type : TypeOfMessage.values()) {
            if (type.getString().equals(value)){
                return type;
            }
        }
        return null;
    }
}

