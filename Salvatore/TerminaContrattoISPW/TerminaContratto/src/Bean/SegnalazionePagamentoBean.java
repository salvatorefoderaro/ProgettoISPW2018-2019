/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author root
 */
public class SegnalazionePagamentoBean {
    private int ID;
    private int IDContratto;
    private int IDLocatario;
    private int IDLocatore;
    private int NumeroReclamo;
    private String ScadenzaReclamo;
    private int Stato;
    
    public int getID(){return this.ID;}
    public int getIDContratto(){return this.IDContratto;}
    public int getIDLocatario(){return this.IDLocatario;}
    public int getIDLocatore(){return this.IDLocatore;}
    public int getNumeroReclamo(){return this.NumeroReclamo;}
    public String getScadenzaReclamo(){return this.ScadenzaReclamo;}
    public int getStato(){return this.Stato;}
    
    public void setID(int ID){this.ID = ID;}
    public void setIDContratto(int IDContratto){this.IDContratto = IDContratto;}
    public void setIDLocatario(int IDLocatario){this.IDLocatario = IDLocatario;}
    public void setIDLocatore(int IDLocatore){this.IDLocatore = IDLocatore;}
    public void setNumeroReclamo(int NumeroReclamo){this.NumeroReclamo = NumeroReclamo;}
    public void setScadenzaReclamo(String ScadenzaReclamo){this.ScadenzaReclamo = ScadenzaReclamo;}
    public void setStato(int Stato){this.Stato = Stato;}
}

