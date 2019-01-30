package Controller;

import Bean.contractBean;
import Bean.notificationBean;
import Bean.paymentClaimBean;
import Bean.userSessionBean;
import DAO.*;
import Entity.*;
import Exceptions.dbConnection;
import Exceptions.emptyResult;
import Exceptions.transactionError;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Controller extends Observable implements Runnable {
    
    private  Map<Integer, PaymentClaim> dictionarySegnalazionePagamento  = new HashMap<Integer, PaymentClaim>();
    private  Map<Integer, Contract> dictionaryContratto  = new HashMap<Integer, Contract>();
    private  userSessionBean loggedUser;

    public Controller()  throws SQLException{
        databaseConnection.getConnectionUser();
    }
    
    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult {
        loggedUser = userJDBC.getInstance("user").login(loginUser);
        return loggedUser;
    }
    
    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws emptyResult, SQLException {
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance("user").getPaymentClaims(bean);
        if (Result.isEmpty()){
            throw new emptyResult("");
        }
        for (paymentClaimBean temp : Result) {
            if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
                Contract trueContract = null;
                if (dictionaryContratto.get(temp.getContractId()) == null){
                    contractBean contract = new contractBean();
                    contract.setContractId(temp.getContractId());
                    contract = contractJDBC.getInstance("user").getContract(contract);
                    trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
                    dictionaryContratto.put(temp.getContractId(), trueContract);
                } else {
                    trueContract = dictionaryContratto.get(temp.getClaimId());
                }

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "" );
                user = userJDBC.getInstance("user").getTenant(user);
                Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
                PaymentClaim claim = new PaymentClaim(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
    return Result;
}

    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult {

        contractJDBC jdbcContratto = contractJDBC.getInstance("user");
        List<contractBean> Result = jdbcContratto.getContracts(user);

        for (contractBean temp : Result) {
            Contract contract = new Contract(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            dictionaryContratto.put(temp.getContractId(), contract);
        }
        return Result;
    }
  
    public void setContrattoArchiviato(paymentClaimBean bean) throws dbConnection, transactionError {
        try {
            contractJDBC.getInstance("admin").setContrattoArchiviato(dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new dbConnection("");
        }

    }
    
    public void setSegnalazioneNotificata(paymentClaimBean bean) throws dbConnection, transactionError {
        try{
            paymentClaimJDBC.getInstance("admin").setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new dbConnection("");
        }

    }

    public void setPaymentClaimPayed(paymentClaimBean bean) throws transactionError, dbConnection {
       try{
            paymentClaimJDBC.getInstance("admin").setSegnalazionePagamentoNotificata(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
       } catch (SQLException e) {
            e.printStackTrace();
            throw new dbConnection("");
           }
    }

    public void insertNewPaymentClaim(paymentClaimBean bean) throws SQLException, dbConnection, transactionError {
        Contract trueContract = null;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance("user").getContract(contract);
            trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getClaimId());
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "");
        user = userJDBC.getInstance("user").getTenant(user);
        Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
        PaymentClaim claim = new PaymentClaim(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
        dictionarySegnalazionePagamento.put(bean.getClaimId(), claim);

        try {
        paymentClaimJDBC.getInstance("admin").createPaymentClaim(bean);
        contractJDBC.getInstance("admin").setContrattoSegnalato(dictionaryContratto.get(bean.getContractId()).makeBean());
        }
        catch (SQLException e) {
                e.printStackTrace();
                throw new transactionError("");
            }
        }

    public void incrementaSegnalazione(paymentClaimBean bean) throws dbConnection, transactionError {
        try{
        paymentClaimJDBC.getInstance("admin").incrementaNumeroSegnalazione(dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean());
            contractJDBC.getInstance("admin").getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new dbConnection("");
        }
    }

    public void checkNotifications(){
        List<paymentClaimBean> Result = null;
        try {
            Result = paymentClaimJDBC.getInstance("user").getPaymentClaims(loggedUser);
        } catch (SQLException ex ) {

        } catch (Exceptions.emptyResult emptyResult) {
            emptyResult.printStackTrace();
        }

        if (!Result.isEmpty()){
            int count = Result.size();
            notificationBean changes = new notificationBean();
            changes.setNotificationsNumber(count);
            setChanged();
            notifyObservers(changes);
        }
    }

    public void checkPaymentClaimDateScadenza(){
        try {
            paymentClaimJDBC.getInstance("user").checkPaymentClaimDate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while(true){
            checkNotifications();
            checkPaymentClaimDateScadenza();
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
