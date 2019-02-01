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

public class Controller {

    private  Map<Integer, PaymentClaim> dictionarySegnalazionePagamento  = new HashMap<Integer, PaymentClaim>();
    private  Map<Integer, Contract> dictionaryContratto  = new HashMap<Integer, Contract>();
    private  userSessionBean loggedUser;

    public Controller()  throws SQLException{
        databaseConnection.getConnectionUser();
    }

    public userSessionBean login(userSessionBean loginUser) throws SQLException, emptyResult {
        return userJDBC.getInstance().login(loginUser);
    }

    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws emptyResult, SQLException {
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

                userSessionBean user = new userSessionBean(temp.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "", null );
                user = userJDBC.getInstance().getTenant(user);
                Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
                PaymentClaim claim = new PaymentClaim(temp.getClaimId(), trueContract, temp.getRenterNickname(), trueTenant, temp.getClaimNumber(), temp.getClaimDeadline(), temp.getClaimState(), temp.getClaimNotified());
                dictionarySegnalazionePagamento.put(temp.getClaimId(), claim);
            }
        }
        return Result;
    }

    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult {

        contractJDBC jdbcContratto = contractJDBC.getInstance();
        List<contractBean> Result = jdbcContratto.getContracts(user);

        for (contractBean temp : Result) {
            Contract contract = new Contract(temp.getContractId(), temp.getContractState(), temp.getTenantNickname(), temp.getRenterNickname());
            dictionaryContratto.put(temp.getContractId(), contract);
        }
        return Result;
    }

    public void setContrattoArchiviato(paymentClaimBean bean) throws transactionError, SQLException {
        contractBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).getContract().makeBean();
        operationBean.setJDBCcommit(true);
        contractJDBC.getInstance().setContrattoArchiviato(operationBean);
    }

    public void setSegnalazioneNotificata(paymentClaimBean bean) throws transactionError, SQLException {
        paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
        operationBean.setJDBCcommit(true);
        paymentClaimJDBC.getInstance().setSegnalazionePagamentoNotificata(operationBean);
    }

    public void setPaymentClaimPayed(paymentClaimBean bean) throws transactionError, SQLException {
        paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
        operationBean.setJDBCcommit(true);
        paymentClaimJDBC.getInstance().setSegnalazionePagamentoNotificata(operationBean);
    }

    public void insertNewPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError {
        Contract trueContract = null;
        if (dictionaryContratto.get(bean.getContractId()) == null){
            contractBean contract = new contractBean();
            contract.setContractId(bean.getContractId());
            contract = contractJDBC.getInstance().getContract(contract);
            trueContract = new Contract(contract.getContractId(), contract.getContractState(), contract.getTenantNickname(), contract.getRenterNickname());
            dictionaryContratto.put(bean.getContractId(), trueContract);
        } else {
            trueContract = dictionaryContratto.get(bean.getClaimId());
        }

        userSessionBean user = new userSessionBean(bean.getTenantNickname(), 0, TypeOfUser.TENANT, 0, "", null);
        user = userJDBC.getInstance().getTenant(user);
        Tenant trueTenant = new Tenant(user.getId(), user.getNickname(), user.getPaymentClaim());
        PaymentClaim claim = new PaymentClaim(bean.getClaimId(), trueContract, bean.getRenterNickname(), trueTenant, bean.getClaimNumber(), bean.getClaimDeadline(), bean.getClaimState(), bean.getClaimNotified());
        dictionarySegnalazionePagamento.put(bean.getClaimId(), claim);

        bean.setJDBCcommit(false);
        paymentClaimJDBC.getInstance().createPaymentClaim(bean);
        contractBean operationBean = dictionaryContratto.get(bean.getContractId()).makeBean();
        operationBean.setJDBCcommit(true);
        contractJDBC.getInstance().setContrattoSegnalato(operationBean);
    }

    public void incrementaSegnalazione(paymentClaimBean bean) throws transactionError, SQLException {
        paymentClaimBean operationBean = dictionarySegnalazionePagamento.get(bean.getClaimId()).makeBean();
        operationBean.setJDBCcommit(true);
        paymentClaimJDBC.getInstance().incrementaNumeroSegnalazione(operationBean);
    }

}
