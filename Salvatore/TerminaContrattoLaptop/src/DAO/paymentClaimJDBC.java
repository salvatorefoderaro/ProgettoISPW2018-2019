/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.paymentClaimBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Bean.userSessionBean;
import Entity.TypeOfUser;
import Exceptions.emptyResult;
//import Bean.BeanTest;
 
public class paymentClaimJDBC implements paymentClaimDAO {
    
    Connection connection = null;
    private static paymentClaimJDBC instance = null;
    
    public static paymentClaimJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new paymentClaimJDBC(type);
        return instance;
    }
    
    private paymentClaimJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        }
        if(type == "admin"){
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection() { return connection; }

    @Override
    public List<paymentClaimBean> getPaymentClaims(userSessionBean bean) throws SQLException, emptyResult {

        
    List<paymentClaimBean> claimsList = new LinkedList<>();
            String query;

            if (TypeOfUser.TENANT == bean.getUserType()){
                query = "SELECT Claim.id, Claim.contractID, Claim.claimNumber, Claim.claimDeadline, Claim.claimState, Claim.claimNotified, Contract.tenantNickname, Contract.renterNickname FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and tenantNickname= ?  and Contract.claimReported = 0";
            } else {
                query = "SELECT Claim.id, Claim.contractID, Claim.claimNumber, Claim.claimDeadline, Claim.claimState, Claim.claimNotified, Contract.tenantNickname, Contract.renterNickname FROM PaymentClaim as Claim JOIN Contract ON Claim.contractID = Contract.contractID and renterNickname= ?  and Contract.claimReported = 0";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, bean.getNickname());
                ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
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

            return claimsList;
    }
    }
    


    @Override
    public void incrementaNumeroSegnalazione(paymentClaimBean bean) throws SQLException {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE PaymentClaim SET claimNumber = claimNumber + 1, claimState = 0, claimDeadline = DATE_ADD(CURDATE(), interval 14 day)  WHERE id = ?");
            preparedStatement.setInt(1, bean.getClaimId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

    }

    @Override
    public void createPaymentClaim(paymentClaimBean bean) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement("ISERT INTO PaymentClaim (contractID, claimNumber, claimDeadline, claimState, claimNotified) VALUES (?, 1, ?, 0, 0))");
            preparedStatement.setString(1,  Integer.toString(bean.getContractId()));
            preparedStatement.setString(2,  bean.getClaimDeadline());
            preparedStatement.executeUpdate();
            preparedStatement.close();

    }
    
    @Override
    public void setSegnalazionePagata(paymentClaimBean bean)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE PaymentClaim SET claimState = 4  WHERE id = ?");
            preparedStatement.setInt(1, bean.getClaimId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

    }



    @Override
    public void setSegnalazionePagamentoArchiviata(paymentClaimBean bean) throws SQLException {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE PaymentClaim SET claimState = 2 WHERE id = ?");
            preparedStatement.setInt(1, bean.getClaimId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
    }
    
    @Override
    public void setSegnalazionePagamentoNotificata(paymentClaimBean bean) throws SQLException {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE PaymentClaim SET claimNotified = 1 WHERE id = ?");
            preparedStatement.setInt(1, bean.getClaimId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
    }
    
    @Override
    public void checkPaymentClaimDate() throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE PaymentClaim SET claimState = 1 WHERE claimDeadline < CURDATE() and claimNumber != 3");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE PaymentClaim SET claimState = 2 WHERE claimDeadline < CURDATE() and claimNumber = 3 and claimState != 3");
        preparedStatement1.executeUpdate();
        preparedStatement1.close();
    }
    
    public void closeConnection(){
        try {
              if (connection != null) {
                  connection.close();
              }
            } catch (Exception e) { 
                //do nothing
            }
    }
}