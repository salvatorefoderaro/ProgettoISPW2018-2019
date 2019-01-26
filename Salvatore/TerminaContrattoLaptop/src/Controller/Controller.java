/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.notificationBean;
import Bean.contractBean;
import Bean.paymentClaimBean;
import Bean.userSessionBean;
import DAO.contractJDBC;
import DAO.paymentClaimJDBC;
import DAO.databaseConnection;
import DAO.tenantJDBC;
import Entity.*;

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
    
    private  Map<Integer, SegnalazionePagamento> dictionarySegnalazionePagamento  = new HashMap<Integer, SegnalazionePagamento>();
    private  Map<Integer, Contratto> dictionaryContratto  = new HashMap<Integer, Contratto>();
    private Connection connection = null;
    private paymentClaimJDBC jdbcSegnalazionePagamento;
    private contractJDBC jdbcContratto;
    private user loggedUser;
    
    public Controller()  throws SQLException{
        databaseConnection.getConnection();
    }
    
    public userSessionBean fakeLogin() throws SQLException{
        
        user newUser = new Locatore(10, "Pasquale");
        loggedUser = newUser;
        userSessionBean bean = new userSessionBean("Pasquale", 10, newUser.getClass().getSimpleName(), 0);
        
        return bean;
    }
    
    public List<paymentClaimBean> getSegnalazioniPagamento(userSessionBean bean) throws SQLException{
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance().getSegnalazioniPagamento(bean);

        for (paymentClaimBean temp : Result) {
            if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
                Contratto trueContract = null;
                if (dictionaryContratto.get(temp.getContractId()) == null){
                    contractBean contract = new contractBean();
                    contract.setContractId(temp.getContractId());
                    contract = contractJDBC.getInstance().getContratto(contract);
                    trueContract = new Contratto(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
                    dictionaryContratto.put(temp.getContractId(), trueContract);
                } else {
                    trueContract = dictionaryContratto.get(temp.getClaimId());
                }

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, "tenant", 0);
                user = tenantJDBC.getInstance().getLocatario(user);
                Locatario trueTenant = new Locatario(user.getId(), user.getNickname(), user.getPaymentClaim());
                SegnalazionePagamento claim = new SegnalazionePagamento(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
    return Result;
}

    public List<contractBean> getContratti(userSessionBean user) throws SQLException{

        contractJDBC jdbcContratto = contractJDBC.getInstance();
        List<contractBean> Result = jdbcContratto.getContratti(user);

        for (contractBean temp : Result) {
            Contratto contract = new Contratto(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            contractBean bean = contract.makeBean();
            dictionaryContratto.put(temp.getContractId(), contract);
        }        
        
        return Result;
    }
  
    public void setContrattoArchiviato(paymentClaimBean bean) throws SQLException{
        contractJDBC.getInstance().setContrattoArchiviato(dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean());
}
    
        public void setSegnalazioneNotificata(paymentClaimBean bean) throws SQLException{

            paymentClaimJDBC.getInstance().setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
}

        public void setSegnalazionePagata(paymentClaimBean bean) throws SQLException{
            paymentClaimJDBC.getInstance().setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
}

    public void inserisciSegnalazionePagamento(paymentClaimBean bean) throws SQLException{

        jdbcSegnalazionePagamento  = paymentClaimJDBC.getInstance();
        jdbcContratto = contractJDBC.getInstance();

        jdbcSegnalazionePagamento.createSegnalazionePagamento(bean);
        jdbcContratto.setContrattoSegnalato(dictionaryContratto.get(bean.getContractId()).makeBean());

        //SegnalazionePagamento newSegnalazione = jdbcSegnalazionePagamento.getSegnalazionePagamento(bean.getContractId());
        //dictionarySegnalazionePagamento.put(newSegnalazione.getClaimId(), newSegnalazione);
    }

    public void incrementaSegnalazione(paymentClaimBean bean)  throws SQLException{
        paymentClaimJDBC.getInstance().incrementaNumeroSegnalazione(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
    }
    
    @Override
    public void run(){
    /*        try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                jdbcSegnalazionePagamento.closeConnection();
            }
       
        int count = 0;
        while(true){
            List<SegnalazionePagamento> Result = null;
                try {
                    Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(userSessionBean.getSession().getNickname(), userSessionBean.getSession().getType());
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
            }*/
        }      
}
