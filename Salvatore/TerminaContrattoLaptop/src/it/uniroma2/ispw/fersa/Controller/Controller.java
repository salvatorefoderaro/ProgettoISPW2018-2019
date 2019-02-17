package it.uniroma2.ispw.fersa.Controller;

import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.Bean.notificationBean;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.*;
import it.uniroma2.ispw.fersa.Entity.*;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Controller extends Observable implements Runnable {
    
    private  Map<Integer, PaymentClaim> dictionarySegnalazionePagamento  = new HashMap<>();
    private  Map<Integer, Contract> dictionaryContratto  = new HashMap<>();
    private  userSessionBean loggedUser;

    public Controller(userSessionBean user){ this.loggedUser = user; }

    public void setTypeOfUSer(TypeOfUser type){ this.loggedUser.setTypeOfUser(type); }
    
    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult, dbConfigMissing {
        return userJDBC.getInstance().login(loginUser);
    }
    
    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws emptyResult, SQLException, dbConfigMissing {
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance().getPaymentClaims(bean);
        for (paymentClaimBean temp : Result) {
            System.out.println("AA" + temp.getContractId());

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

        List<contractBean> Result = contractJDBC.getInstance().getContracts(user);

        for (contractBean temp : Result) {

            Contract contract = new Contract(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            dictionaryContratto.put(temp.getContractId(), contract);
            if (temp.getContractId() == 8){
            }
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
        operationBean.setJDBCcommit(false);
        paymentClaimJDBC.getInstance().setPaymentClaimPayed(operationBean);

        System.out.println(bean.getContractId());

        contractBean contractOperation = dictionaryContratto.get(bean.getContractId()).makeBean();
        contractOperation.setJDBCcommit(true);
        contractJDBC.getInstance().setContractNotClaimed(contractOperation);

    }

    public void insertNewPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing, alreadyClaimed {
        Contract trueContract;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance().getContract(contract);
            trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getContractId());
        }

        if (trueContract.getClaimed() == true){
            throw new alreadyClaimed("aa");
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "");
        user = userJDBC.getInstance().getTenant(user);
        Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
        PaymentClaim claim = new PaymentClaim(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
        dictionarySegnalazionePagamento.put(bean.getClaimId(), claim);

        user.setJDBCcommit(false);
        userJDBC.getInstance().incrementPaymentClaimNumber(user);

        bean.setJDBCcommit(false);
        paymentClaimJDBC.getInstance().createPaymentClaim(bean);
        contractBean operationBean = dictionaryContratto.get(bean.getContractId()).makeBean();
        operationBean.setJDBCcommit(true);
        contractJDBC.getInstance().setContractReported(operationBean);

        trueContract.setClaimed();
    }

    public void incrementPaymentClaim(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
            paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
            operationBean.setJDBCcommit(true);
            paymentClaimJDBC.getInstance().incrementPaymentClaimNumber(operationBean);
    }

    public void checkNotifications() throws SQLException, emptyResult, dbConfigMissing {
            notificationBean Result = paymentClaimJDBC.getInstance().getPaymentClaimCount(loggedUser);
            int count = Result.getNotificationsNumber();
            if (count > 0) {
                setChanged();
                notifyObservers(Result);
            }
        }

    @Override
    public void run() {

        while(true){

            try {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Checking notifications...");
                checkNotifications();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Errore nella comunicazione con il DB");
            } catch (it.uniroma2.ispw.fersa.Exceptions.emptyResult emptyResult) {
            } catch (it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing dbConfigMissing) {
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " | Configurazione per il DB assente");
            }

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
