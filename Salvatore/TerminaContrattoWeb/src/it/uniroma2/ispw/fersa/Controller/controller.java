package it.uniroma2.ispw.fersa.Controller;

import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.Bean.notificationBean;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.*;
import it.uniroma2.ispw.fersa.Entity.*;
import it.uniroma2.ispw.fersa.Entity.Enum.typeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.alreadyClaimed;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class controller {

    private  Map<Integer, paymentClaim> dictionarySegnalazionePagamento  = new HashMap<Integer, paymentClaim>();
    private  Map<Integer, contract> dictionaryContratto  = new HashMap<Integer, contract>();
    private  userSessionBean loggedUser;

    public controller(){  }

    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult, dbConfigMissing {
        return userJDBC.getInstance().login(loginUser);
    }

    public notificationBean getPaymentClaimNumber(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing {
       return paymentClaimJDBC.getInstance().getPaymentClaimCount(bean);
    }

    public void setTypeOfUSer(typeOfUser type){ this.loggedUser.setUserType(type); }


    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws emptyResult, SQLException, dbConfigMissing {
        List<paymentClaimBean> Result = paymentClaimJDBC.getInstance().getPaymentClaims(bean);
        for (paymentClaimBean temp : Result) {
            if (dictionarySegnalazionePagamento.get(temp.getClaimId()) == null){
                contract trueContract = null;
                if (dictionaryContratto.get(temp.getContractId()) == null){
                    contractBean contract = new contractBean();
                    contract.setContractId(temp.getContractId());
                    contract = contractJDBC.getInstance().getContract(contract);
                    trueContract = new contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
                    dictionaryContratto.put(temp.getContractId(), trueContract);
                } else {
                    trueContract = dictionaryContratto.get(temp.getClaimId());
                }

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, typeOfUser.TENANT, 0, "", null );
                user = userJDBC.getInstance().getTenant(user);
                tenant trueTenant = new tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
                paymentClaim claim = new paymentClaim(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
        return Result;
    }

    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult, dbConfigMissing {

        contractJDBC jdbcContratto = contractJDBC.getInstance();
        List<contractBean> Result = jdbcContratto.getContracts(user);

        for (contractBean temp : Result) {
            contract contract = new contract(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            dictionaryContratto.put(temp.getContractId(), contract);
        }
        return Result;
    }

    public void setContractAchieved(paymentClaimBean bean) throws transactionError, SQLException, dbConfigMissing {
        contractBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean();
        operationBean.setJDBCcommit(true);
        contractJDBC.getInstance().setContractAchieved(operationBean);
        dictionarySegnalazionePagamento.remove(bean.getClaimId());
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

        contractBean contractOperation = dictionaryContratto.get(bean.getContractId()).makeBean();
        contractOperation.setJDBCcommit(true);
        contractJDBC.getInstance().setContractNotClaimed(contractOperation);

    }

    public void insertNewPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing, alreadyClaimed {
        contract trueContract;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance().getContract(contract);
            trueContract = new contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getContractId());
        }

        if (trueContract.getClaimed() == true){
            throw new alreadyClaimed("aa");
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, typeOfUser.TENANT, 0, "", null);
        user = userJDBC.getInstance().getTenant(user);
        tenant trueTenant = new tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
        paymentClaim claim = new paymentClaim(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
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

    public void checkPaymentClaimDateScadenza() throws dbConfigMissing, SQLException {
        paymentClaimJDBC.getInstance().checkPaymentClaimDate();
    }

}
