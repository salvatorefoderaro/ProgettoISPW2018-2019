package Controller;

import Bean.contractBean;
import Bean.notificationBean;
import Bean.paymentClaimBean;
import Bean.userSessionBean;
import DAO.*;
import Entity.*;
import Exceptions.dbConfigMissing;
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

    public Controller(userSessionBean user){ this.loggedUser = user; }
    
    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult, dbConfigMissing {
        return userJDBC.getInstance().login(loginUser);
    }
    
    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws emptyResult, SQLException, dbConfigMissing {
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance().getPaymentClaims(bean);
        for (paymentClaimBean temp : Result) {
            if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
                Contract trueContract = null;
                if (dictionaryContratto.get(temp.getContractId()) == null){
                    contractBean contract = new contractBean();
                    contract.setContractId(temp.getContractId());
                    contract = contractJDBC.getInstance().getContract(contract);
                    trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
                    dictionaryContratto.put(temp.getContractId(), trueContract);
                } else {
                    trueContract = dictionaryContratto.get(temp.getClaimId());
                }

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "" );
                user = userJDBC.getInstance().getTenant(user);
                Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
                PaymentClaim claim = new PaymentClaim(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
    return Result;
}

    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult, dbConfigMissing {

        contractJDBC jdbcContratto = contractJDBC.getInstance();
        List<contractBean> Result = jdbcContratto.getContracts(user);

        for (contractBean temp : Result) {
            Contract contract = new Contract(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            dictionaryContratto.put(temp.getContractId(), contract);
        }
        return Result;
    }
  
    public void setContractAchieved(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
        contractBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean();
        operationBean.setJDBCcommit(true);
        contractJDBC.getInstance().setContractAchieved(operationBean);
        dictionaryContratto.remove(bean.getClaimId());
    }
    
    public void setPaymentClaimNotified(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
        paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
        operationBean.setJDBCcommit(true);
        paymentClaimJDBC.getInstance().setPaymentClaimNotified(operationBean);
        dictionarySegnalazionePagamento.remove(bean.getClaimId());
    }

    public void setPaymentClaimPayed(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
        paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
        operationBean.setJDBCcommit(true);
        paymentClaimJDBC.getInstance().setPaymentClaimNotified(operationBean);
    }

    public void insertNewPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {
        Contract trueContract;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance().getContract(contract);
            trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getClaimId());
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "");
        user = userJDBC.getInstance().getTenant(user);
        Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
        PaymentClaim claim = new PaymentClaim(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
        dictionarySegnalazionePagamento.put(bean.getClaimId(), claim);

        bean.setJDBCcommit(false);
        paymentClaimJDBC.getInstance().createPaymentClaim(bean);
        contractBean operationBean = dictionaryContratto.get(bean.getContractId()).makeBean();
        operationBean.setJDBCcommit(false);
        contractJDBC.getInstance().setContractReported(operationBean);
        }

    public void incrementPaymentClaim(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
            paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
            operationBean.setJDBCcommit(true);
            paymentClaimJDBC.getInstance().incrementPaymentClaimNumber(operationBean);
    }

    public void checkNotifications() throws SQLException, emptyResult, dbConfigMissing {
        List<paymentClaimBean> Result;
            Result = paymentClaimJDBC.getInstance().getPaymentClaims(loggedUser);
            int count = Result.size();
            notificationBean changes = new notificationBean();
            changes.setNotificationsNumber(count);
            setChanged();
            notifyObservers(changes);
        }

    public void checkPaymentClaimDateScadenza() throws dbConfigMissing, SQLException {
            paymentClaimJDBC.getInstance().checkPaymentClaimDate();
    }

    @Override
    public void run() {

        while(true){
            System.out.println("faccio il check");
            try {
                checkNotifications();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exceptions.emptyResult emptyResult) {
                System.out.println("Nothing to show...");
            } catch (Exceptions.dbConfigMissing dbConfigMissing) {
                dbConfigMissing.printStackTrace();
            }
            try {
                checkPaymentClaimDateScadenza();
            } catch (Exceptions.dbConfigMissing dbConfigMissing) {
                dbConfigMissing.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
