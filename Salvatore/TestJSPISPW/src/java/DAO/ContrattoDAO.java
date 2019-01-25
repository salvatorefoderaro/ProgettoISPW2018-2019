/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.DAO;

 
import Entity.Contratto;
import java.util.List;
 
import Entity.SegnalazionePagamento;
import java.sql.SQLException;
 
public interface ContrattoDAO {
    public void setContrattoArchiviato(int ID) throws SQLException;
    public Contratto getContratto(int ID) throws SQLException;
    public List<Contratto> getContratti(String renterNickname) throws SQLException;
    public void setContrattoSegnalato(int ID) throws SQLException;
}