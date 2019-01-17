/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import DAO.JDBCContratto;

/**
 *
 * @author root
 */
public class Contratto {
    private int IDContratto;
    private int StatoContratto;
    private JDBCContratto jdbcContratto;
    private int IDLocatario;
    private int IDLocatore;
    
    public Contratto (int IDcontratto, int StatoContratto, int Locatario){
        this.IDContratto = IDcontratto;
        this.StatoContratto = StatoContratto;
        this.jdbcContratto = new JDBCContratto();
        this.IDLocatario = Locatario;
    }
    
    public int getIDContratto(){ return this.IDContratto; } 
    public int getStatoContratto(){ return this.StatoContratto; }
    public int getIDLocatario(){ return this.IDLocatario; }
    
    public void archiviaContratto(){
        this.jdbcContratto.setContrattoArchiviato(this.IDContratto);
    }
    
    public void iMieiDati(){
        System.out.println("Sono: " + Integer.toString(this.IDContratto) + " e " + Integer.toString(this.StatoContratto));
    }
    
}
