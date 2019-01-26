/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.contractBean;
import Bean.paymentClaimBean;
import Bean.userSessionBean;
import DAO.*;
import Entity.*;
import Exceptions.dbConnection;
import Exceptions.emptyResult;
import Exceptions.transactionError;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Controller extends Observable implements Runnable {
    
    private  Map<Integer, SegnalazionePagamento> dictionarySegnalazionePagamento  = new HashMap<Integer, SegnalazionePagamento>();
    private  Map<Integer, Contratto> dictionaryContratto  = new HashMap<Integer, Contratto>();

    public Controller()  throws SQLException{
        databaseConnection.getConnectionUser();
    }
    
    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult {
        return userJDBC.getInstance("user").login(loginUser);
    }
    
    public List<paymentClaimBean> getSegnalazioniPagamento(userSessionBean bean) throws emptyResult, SQLException {
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance("user").getSegnalazioniPagamento(bean);
        if (Result.isEmpty()){
            throw new emptyResult("");
        }
        for (paymentClaimBean temp : Result) {
            if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
                Contratto trueContract = null;
                if (dictionaryContratto.get(temp.getContractId()) == null){
                    contractBean contract = new contractBean();
                    contract.setContractId(temp.getContractId());
                    contract = contractJDBC.getInstance("user").getContratto(contract);
                    trueContract = new Contratto(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
                    dictionaryContratto.put(temp.getContractId(), trueContract);
                } else {
                    trueContract = dictionaryContratto.get(temp.getClaimId());
                }

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, "tenant", 0, "" );
                user = tenantJDBC.getInstance("user").getLocatario(user);
                Locatario trueTenant = new Locatario(user.getId(), user.getNickname(), user.getPaymentClaim());
                SegnalazionePagamento claim = new SegnalazionePagamento(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
    return Result;
}

    public List<contractBean> getContratti(userSessionBean user) throws SQLException, emptyResult {

        contractJDBC jdbcContratto = contractJDBC.getInstance("user");
        List<contractBean> Result = jdbcContratto.getContratti(user);

        for (contractBean temp : Result) {
            Contratto contract = new Contratto(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            contractBean bean = contract.makeBean();
            dictionaryContratto.put(temp.getContractId(), contract);
        }        
        
        return Result;
    }
  
    public void setContrattoArchiviato(paymentClaimBean bean) throws dbConnection, transactionError {
        try {
            contractJDBC.getInstance("admin").setContrattoArchiviato(dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean());
            contractJDBC.getInstance("admin").getConnection().commit();
        } catch (SQLException e) {
            try {
                contractJDBC.getInstance("admin").getConnection().rollback();
                throw new transactionError("");
            } catch (SQLException e1) {
                throw new dbConnection("");
            }
        }
    }
    
        public void setSegnalazioneNotificata(paymentClaimBean bean) throws SQLException, dbConnection, transactionError {
        try{
            paymentClaimJDBC.getInstance("admin").setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
            contractJDBC.getInstance("admin").getConnection().commit();
        } catch (SQLException e) {
            try {
                contractJDBC.getInstance("admin").getConnection().rollback();
                throw new transactionError("");
            } catch (SQLException e1) {
                throw new dbConnection("");
            }
        }

}

        public void setSegnalazionePagata(paymentClaimBean bean) throws transactionError, dbConnection {
           try{
            paymentClaimJDBC.getInstance("admin").setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
               contractJDBC.getInstance("admin").getConnection().commit();
           } catch (SQLException e) {
               try {
                   contractJDBC.getInstance("admin").getConnection().rollback();
                   throw new transactionError("");
               } catch (SQLException e1) {
                   throw new dbConnection("");
               }
           }
    }

    public void inserisciSegnalazionePagamento(paymentClaimBean bean) throws SQLException, dbConnection, transactionError {

        Contratto trueContract = null;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance("user").getContratto(contract);
            trueContract = new Contratto(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getClaimId());
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, "tenant", 0, "");
        user = tenantJDBC.getInstance("user").getLocatario(user);
        Locatario trueTenant = new Locatario(user.getId(), user.getNickname(), user.getPaymentClaim());
        SegnalazionePagamento claim = new SegnalazionePagamento(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
        dictionarySegnalazionePagamento.put(bean.getClaimId(), claim);

        try {
        paymentClaimJDBC.getInstance("admin").createSegnalazionePagamento(bean);
        contractJDBC.getInstance("admin").setContrattoSegnalato(dictionaryContratto.get(bean.getContractId()).makeBean());
        contractJDBC.getInstance("admin").getConnection().commit();


    }    catch (SQLException e) {
    try {
        contractJDBC.getInstance("admin").getConnection().rollback();
        throw new transactionError("");
    } catch (SQLException e1) {
        throw new dbConnection("");
    }
        }

    }


    public void incrementaSegnalazione(paymentClaimBean bean) throws dbConnection, transactionError {
        try{
        paymentClaimJDBC.getInstance("admin").incrementaNumeroSegnalazione(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
            contractJDBC.getInstance("admin").getConnection().commit();
        } catch (SQLException e) {
            try {
                contractJDBC.getInstance("admin").getConnection().rollback();
                throw new transactionError("");
            } catch (SQLException e1) {
                throw new dbConnection("");
            }
        }
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
