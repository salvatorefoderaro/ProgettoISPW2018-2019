/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.notificationBean;
import Bean.contractBean;
import Bean.paymentClaimBean;
import Boundary.userSession;
import DAO.contractJDBC;
import DAO.paymentClaimJDBC;
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
    private paymentClaimJDBC jdbcSegnalazionePagamento;
    private contractJDBC jdbcContratto;
    private static Controller controller_instance = null;
    
    private Controller()  throws SQLException{
        
        databaseConnection.getConnection();
    }
    
    public static Controller getInstance()  throws SQLException {
        if (controller_instance == null)
                controller_instance = new Controller();
        return controller_instance;
    }
    
    public List<paymentClaimBean> getSegnalazioniPagamento(String nickname, String type) throws SQLException{
        
    jdbcSegnalazionePagamento = new paymentClaimJDBC();
    
    List<SegnalazionePagamento> Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(nickname, type);
    List<paymentClaimBean> list = new LinkedList<paymentClaimBean>();
    
    for (SegnalazionePagamento temp : Result) {
        if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
            dictionarySegnalazionePagamento.put(temp.getClaimId(), temp);    
        }
        paymentClaimBean bean = new paymentClaimBean();
        bean.setClaimDeadline(temp.getClaimDeadline());
        bean.setClaimId(temp.getClaimId());
        bean.setClaimNumber(temp.getClaimNumber());
        bean.setClaimState(temp.getClaimState());
        bean.setContractId(temp.getContract().getContractId());
        bean.setRenterNickname(nickname);
        bean.setTenantNickname(temp.getTenantNickname().getNickname());
        list.add(bean);
    }
    
    return list;    
}   
    
    public List<contractBean> getContratti(String renterNickname) throws SQLException{
        
        contractJDBC jdbcContratto = new contractJDBC();
        List<Contratto> Result = jdbcContratto.getContratti(renterNickname);
        List<contractBean> list = new LinkedList<>();
        
        for (Contratto temp : Result) {
            contractBean bean = new contractBean();
            bean.setContractId(temp.getContractId());
            bean.setTenantNickname(temp.getTenantNickname());
            bean.setRenterNickname(temp.getRenterNickname());
            bean.setContractState(temp.getContractState());
            list.add(bean);
            dictionaryContratto.put(temp.getContractId(), temp);
        }        
        
        return list;
    }
  
    public void setContrattoArchiviato(paymentClaimBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getClaimId()).getContratto().archiviaContratto();            
}
    
        public void setSegnalazioneNotificata(paymentClaimBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getClaimId()).archiviaNotificaSegnalazione();
}
        
                public void setSegnalazionePagata(paymentClaimBean bean) throws SQLException{
        dictionarySegnalazionePagamento.get(bean.getClaimId()).archiviaNotificaSegnalazione();
}
    
    public void inserisciSegnalazionePagamento(paymentClaimBean bean) throws SQLException{
       
        jdbcSegnalazionePagamento  = new paymentClaimJDBC();
        jdbcContratto = new contractJDBC();
        
        jdbcSegnalazionePagamento.createSegnalazionePagamento(bean);
        jdbcContratto.setContrattoSegnalato(bean.getContractId());

        SegnalazionePagamento newSegnalazione = jdbcSegnalazionePagamento.getSegnalazionePagamento(bean.getContractId());
        dictionarySegnalazionePagamento.put(newSegnalazione.getClaimId(), newSegnalazione);
    }
    
    public void incrementaSegnalazione(paymentClaimBean bean)  throws SQLException{
        //if (bean.getNumeroReclamo() == 3){
          //  dictionarySegnalazionePagamento.get(bean.getID()).archiviaSegnalazionePagamento();
        
            dictionarySegnalazionePagamento.get(bean.getClaimId()).incrementaSegnalazionePagamento();
        
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
                    Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(userSession.getSession().getUsername(), userSession.getSession().getType());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {    
                    jdbcSegnalazionePagamento.checkSegnalazionePagamentoData();
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            if (!Result.isEmpty()){
                count = Result.size();
                notificationBean changes = new notificationBean();
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
