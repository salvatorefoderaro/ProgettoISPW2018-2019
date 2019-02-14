package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.notificationBean;
import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.DAO.Configuration.transactionConnection;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;

public class paymentClaimJDBC implements paymentClaimDAO {

    public static paymentClaimJDBC getInstance() {
        return paymentClaimJDBC.trueInstance.instance;
    }

    private static class trueInstance {
        private final static paymentClaimJDBC instance = new paymentClaimJDBC();
    }

    private paymentClaimJDBC(){ }

    @Override
    public notificationBean getPaymentClaimCount(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        String query;

        if (TypeOfUser.TENANT == bean.getUserType()){
            query = "SELECT COUNT(*) as Number FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and tenantNickname= ?  and Claim.claimNotified = 0 and (Claim.claimState = 0 or Claim.claimState = 3)";
        } else {
            query = "SELECT COUNT(*) as Number FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and renterNickname= ?  and Claim.claimNotified = 0 and (Claim.claimState = 1 or Claim.claimState = 2 or Claim.claimState = 4)";
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, bean.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        if(resultSet.getInt("Number") == 0){
            throw new emptyResult("Errore! Nessuna segnalazione di pagamento al momento disponibile!");
        }
        notificationBean numberNotify = new notificationBean();
        numberNotify.setNotificationNumber(resultSet.getInt("Number"));
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return numberNotify;

    }

    @Override
    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        List<paymentClaimBean> claimsList = new LinkedList<>();
        String query;

        if (TypeOfUser.TENANT == bean.getUserType()){
            query = "SELECT Claim.id, Claim.contractID, Claim.claimNumber, Claim.claimDeadline, Claim.claimState, Claim.claimNotified, Contract.tenantNickname, Contract.renterNickname FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and tenantNickname= ?  and Claim.claimNotified = 0";
        } else {
            query = "SELECT Claim.id, Claim.contractID, Claim.claimNumber, Claim.claimDeadline, Claim.claimState, Claim.claimNotified, Contract.tenantNickname, Contract.renterNickname FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and renterNickname= ?  and Claim.claimNotified = 0";
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, bean.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessuna segnalazione di pagamento al momento disponibile!");
        }else {
            while(resultSet.next()){
                paymentClaimBean paymentBean = new paymentClaimBean();
                paymentBean.setClaimId(resultSet.getInt("id"));
                paymentBean.setContractId(resultSet.getInt("contractId"));
                paymentBean.setRenterNickname(resultSet.getString("renterNickname"));
                paymentBean.setTenantNickname(resultSet.getString("tenantNickname"));
                paymentBean.setClaimNumber(resultSet.getInt("claimNumber"));
                paymentBean.setClaimDeadline(resultSet.getString("claimDeadline"));
                paymentBean.setClaimState(resultSet.getInt("claimState"));
                paymentBean.setClaimNotified(resultSet.getInt("claimNotified"));

                claimsList.add(paymentBean);
            }
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();

            return claimsList;
        }
    }

    @Override
    public void incrementPaymentClaimNumber(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dBConnection.setAutoCommit(false);

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimNumber = claimNumber + 1, claimState = 0, claimDeadline = DATE_ADD(CURDATE(), interval 14 day)  WHERE id = ?");
        preparedStatement.setInt(1, bean.getClaimId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                dBConnection.close();
            } catch (SQLException e){
                dBConnection.rollback();
                dBConnection.close();
                throw new transactionError("");
            }
        }
    }

    @Override
    public void createPaymentClaim(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = transactionConnection.getConnection();

        PreparedStatement preparedStatement = dBConnection.prepareStatement("INSERT INTO PaymentClaim (contractID, claimNumber, claimDeadline, claimState, claimNotified) VALUES (?, 1, ?, 0, 0) ");
        preparedStatement.setString(1,  Integer.toString(bean.getContractId()));
        preparedStatement.setString(2,  bean.getClaimDeadline());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                transactionConnection.closeConnection();
            } catch (SQLException e){
                dBConnection.rollback();
                transactionConnection.closeConnection();
                throw new transactionError("");
            }
        }
    }

    @Override
    public void setPaymentClaimPayed(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dBConnection.setAutoCommit(false);

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimState = 4  WHERE id = ?");
        preparedStatement.setInt(1, bean.getClaimId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                dBConnection.close();
            } catch (SQLException e){
                dBConnection.rollback();
                dBConnection.close();
                throw new transactionError("");
            }
        }
    }

    @Override
    public void setPaymentClaimAchieved(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dBConnection.setAutoCommit(false);

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimState = 2 WHERE id = ?");
        preparedStatement.setInt(1, bean.getClaimId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                dBConnection.close();
            } catch (SQLException e){
                dBConnection.rollback();
                dBConnection.close();
                throw new transactionError("");
            }
        }
    }

    @Override
    public void setPaymentClaimNotified(paymentClaimBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dBConnection.setAutoCommit(false);

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimNotified = 1 WHERE id = ?");
        preparedStatement.setInt(1, bean.getClaimId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                dBConnection.close();
            } catch (SQLException e){
                dBConnection.rollback();
                dBConnection.close();
                throw new transactionError("");
            }
        }
    }

    @Override
    public void checkPaymentClaimDate() throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimState = 1 WHERE claimDeadline < CURDATE() and claimNumber != 3");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimState = 2 WHERE claimDeadline < CURDATE() and claimNumber = 3 and claimState != 3");
        preparedStatement1.executeUpdate();
        preparedStatement1.close();
    }

}