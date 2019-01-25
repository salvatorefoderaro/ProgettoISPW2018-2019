/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.DAO;

 
import Bean.SegnalazionePagamentoBean;
import java.util.List;
 
import Entity.SegnalazionePagamento;
import java.sql.SQLException;
 
public interface SegnalazionePagamentoDAO {
     
    public void setSegnalazionePagamentoArchiviata(int ID)  throws SQLException;
    public List<SegnalazionePagamento> getSegnalazioniPagamento(String userNickname, String type)  throws SQLException;
    public void incrementaNumeroSegnalazione(int ID)  throws SQLException;
    public void setSegnalazionePagamentoNotificata(int ID)  throws SQLException;
    public void createSegnalazionePagamento(SegnalazionePagamentoBean bean)  throws SQLException;
    public SegnalazionePagamento getSegnalazionePagamento(int ID)  throws SQLException;
    public void checkSegnalazionePagamentoData() throws SQLException;
    public void setSegnalazionePagata(int ID) throws SQLException;
}