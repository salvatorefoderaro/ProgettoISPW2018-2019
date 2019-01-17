package loginexample;

public class Utente {

    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String[] a;
    
    public Utente(String username, String password, String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
    }
}
