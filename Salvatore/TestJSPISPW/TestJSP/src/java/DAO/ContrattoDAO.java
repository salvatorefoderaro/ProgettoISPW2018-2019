/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Entity.Contratto;
import java.util.List;
 
import Entity.SegnalazionePagamento;
 
public interface ContrattoDAO {
    public void setContrattoArchiviato(int ID);
    public Contratto getContratto(int ID);
    public List<Contratto> getContratti(int ID);
    public void setContrattoSegnalato(int ID);
}