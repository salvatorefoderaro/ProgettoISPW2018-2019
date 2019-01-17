/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.BeanNotifica;
import Bean.ContrattoBean;
import Bean.SegnalazionePagamentoBean;
import Boundary.session;
import DAO.JDBCContratto;
import DAO.JDBCSegnalazionePagamento;
import DAO.databaseConnection;
import Entity.Contratto;
import Entity.SegnalazionePagamento;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Observable implements Runnable {
    
    // Creo il dizionario, "accetta" un Intero come chiave, e segnalazione Pagamento come valore (oggetto)
    private  Map<Integer, SegnalazionePagamento> dictionarySegnalazionePagamento  = new HashMap<Integer, SegnalazionePagamento>();
    private  Map<Integer, Contratto> dictionaryContratto  = new HashMap<Integer, Contratto>();
    private Connection connection = null;
    private JDBCSegnalazionePagamento jdbcSegnalazionePagamento;
    private JDBCContratto jdbcContratto;
    private static Controller controller_instance = null;
    
    private Controller()  throws SQLException{
        
        databaseConnection.getConnection();
    }
    
    public static Controller getInstance()  throws SQLException {
        if (controller_instance == null)
                controller_instance = new Controller();
        return controller_instance;
    }
    
    public List<SegnalazionePagamentoBean> getSegnalazioniPagamento(int ID, String type) throws SQLException{
        
    jdbcSegnalazionePagamento = new JDBCSegnalazionePagamento();
    
    List<SegnalazionePagamento> Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(ID, type);
    System.out.println("\n\nLa dimensione è: " + Result.size());
    List<SegnalazionePagamentoBean> list = new LinkedList<SegnalazionePagamentoBean>();
    
    for (SegnalazionePagamento temp : Result) {
        if (dictionarySegnalazionePagamento.get(temp.getID()) == null){
            dictionarySegnalazionePagamento.put(temp.getID(), temp);    
        }
        list.add(temp.makeBean());
    }
    
    return list;    
}   
    
    public List<ContrattoBean> getContratti(int ID) throws SQLException{
        
        JDBCContratto jdbcContratto = new JDBCContratto();
        List<Contratto> Result = jdbcContratto.getContratti(ID);
        List<ContrattoBean> list = new LinkedList<>();
        
        for (Contratto temp : Result) {
            ContrattoBean bean = new ContrattoBean();
            bean.setIDContratto(temp.getIDContratto());
            bean.setIDLocatario(temp.getIDLocatario());
            bean.setStatoContratto(temp.getStatoContratto());
            list.add(bean);
            dictionaryContratto.put(temp.getIDContratto(), temp);
        }        
        
        return list;
    }
  
    public void setContrattoArchiviato(SegnalazionePagamentoBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getID()).getContratto().archiviaContratto();            
}
    
        public void setSegnalazioneNotificata(SegnalazionePagamentoBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getID()).archiviaNotificaSegnalazione();
}
        
                public void setSegnalazionePagata(SegnalazionePagamentoBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getID()).archiviaNotificaSegnalazione();
}
    
    public void testMakeBean(SegnalazionePagamentoBean bean) throws SQLException{
       
        jdbcSegnalazionePagamento  = new JDBCSegnalazionePagamento();
        jdbcContratto = new JDBCContratto();
        
        jdbcSegnalazionePagamento.createSegnalazionePagamento(bean);
        jdbcContratto.setContrattoSegnalato(bean.getIDContratto());

        SegnalazionePagamento newSegnalazione = jdbcSegnalazionePagamento.getSegnalazionePagamento(bean.getIDContratto());
        dictionarySegnalazionePagamento.put(newSegnalazione.getID(), newSegnalazione);
    }
    
    public void incrementaSegnalazione(SegnalazionePagamentoBean bean)  throws SQLException{
        //if (bean.getNumeroReclamo() == 3){
          //  dictionarySegnalazionePagamento.get(bean.getID()).archiviaSegnalazionePagamento();
        
            dictionarySegnalazionePagamento.get(bean.getID()).incrementaSegnalazionePagamento();
        
    }
    
    @Override
    public void run(){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                jdbcSegnalazionePagamento.closeConnection();
            }
       
        int count = 0;
        while(true){
            List<SegnalazionePagamento> Result = null;
                try {
                    Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(session.getSession().getId(), session.getSession().getType());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {    
                    jdbcSegnalazionePagamento.checkSegnalazionePagamentoData();
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            if (Result.size() != 0){
                count = Result.size();
                BeanNotifica changes = new BeanNotifica();
                changes.setNumeroNotifiche(count);
                setChanged();
                notifyObservers(changes);
            } 

            try {
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                jdbcSegnalazionePagamento.closeConnection();
            }
            }
        }       
}
