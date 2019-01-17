/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
import Bean.SegnalazionePagamentoBean;
import DAO.JDBCSegnalazionePagamento;
/**
 *
 * @author root
 */
public class SegnalazionePagamento {
    private int ID;
    private Contratto IDContratto;
    private int IDLocatore;
    private Locatario IDLocatario;
    private int NumeroReclamo;
    private String ScadenzaReclamo;
    private int Stato;
    private int Notified;
    private JDBCSegnalazionePagamento jdbcSegnalazionePagamento;
    
    public SegnalazionePagamento(int ID, Contratto IDContratto, int IDLocatore, Locatario IDLocatario, int NumeroReclamo, String ScadenzaReclamo, int Stato, int Notified){
        this.ID = ID;
        this.IDContratto= IDContratto;
        this.IDLocatore = IDLocatore;
        this.IDLocatario = IDLocatario;
        this.NumeroReclamo = NumeroReclamo;
        this.ScadenzaReclamo = ScadenzaReclamo;
        this.Stato = Stato;
        this.Notified = Notified;
        this.jdbcSegnalazionePagamento = new JDBCSegnalazionePagamento();
    }
    
    public int getID(){
        return this.ID;
    }
    
    public Contratto getContratto(){
        return this.IDContratto;
    }
    
    public void archiviaSegnalazionePagamento(){
        this.jdbcSegnalazionePagamento.setSegnalazionePagamentoArchiviata(this.ID);
    }
    
    public void archiviaNotificaSegnalazione(){
        this.jdbcSegnalazionePagamento.setSegnalazionePagamentoNotificata(this.ID);
    }
    
    public void segnalazionePagamentoPagata(){
        this.jdbcSegnalazionePagamento.setSegnalazionePagata(this.ID);
    }
    
    public void incrementaSegnalazionePagamento(){
        this.jdbcSegnalazionePagamento.incrementaNumeroSegnalazione(this.ID);
    }
    
    public void creaSegnalazionePagamento(){
        //this.jdbcSegnalazionePagamento.createSegnalazionePagamento();
    }
    
    public void updateSegnalazionePagamento(){
        this.jdbcSegnalazionePagamento.incrementaNumeroSegnalazione(this.ID);
    }
        
    public SegnalazionePagamentoBean makeBean(){
        SegnalazionePagamentoBean Bean = new SegnalazionePagamentoBean();
        
        Bean.setID(this.ID);
        Bean.setIDContratto(this.IDContratto.getIDContratto()); 
        Bean.setIDLocatario(this.IDLocatario.getID());
        Bean.setNumeroReclamo(this.NumeroReclamo);
        Bean.setStato(this.Stato);
        Bean.setScadenzaReclamo(this.ScadenzaReclamo);
        Bean.setIDLocatore(this.IDLocatore);

        return Bean;
    }
    
}
