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
public class ContrattoBean {
    
    private int IDContratto;
    private int StatoContratto;
    private int IDLocatario;
    
    public void setIDContratto(int IDContratto){this.IDContratto = IDContratto;}
    public void setStatoContratto(int StatoContratto){this.StatoContratto = StatoContratto;}
    public void setIDLocatario(int IDLocatario){this.IDLocatario = IDLocatario;}
    
    public int getIDContratto(){ return this.IDContratto;}
    public int getStatoContratto(){ return this.StatoContratto; }
    public int getIDLocatario(){ return this.IDLocatario; }
    
}
