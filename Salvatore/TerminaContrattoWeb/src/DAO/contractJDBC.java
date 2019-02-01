/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.contractBean;
import Bean.userSessionBean;
import Exceptions.emptyResult;
import Exceptions.transactionError;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class contractJDBC implements contractDAO {

    private static contractJDBC instance = null;

    public static contractJDBC getInstance() {
        if (instance == null)
            instance = new contractJDBC();
        return instance;
    }

    private contractJDBC(){  }

    @Override
    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult {

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");

        List<contractBean> listBean = new LinkedList<>();
        String query = "select contractID, tenantNickname, renterNickname from Contract where renterNickname = ? and claimReported = 0";
        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, user.getNickname());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("");
        } else {
            while(resultSet.next()){
                contractBean bean = new contractBean();
                bean.setContractId(resultSet.getInt("contractID"));
                bean.setRenterNickname(resultSet.getString("renterNickname"));
                bean.setTenantNickname(resultSet.getString("tenantNickname"));
                listBean.add(bean);
            }
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();

            return listBean;
        }
    }

    @Override
    public contractBean getContract(contractBean bean)  throws SQLException{

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");

        String query = "select tenantNickname, renterNickname, state from Contract where contractID = ? and claimReported = 0";
        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(bean.getContractId()));
        ResultSet resultSet = preparedStatement.executeQuery();
        contractBean contract = new contractBean();
        while(resultSet.next()){
            contract.setContractId(bean.getContractId());
            contract.setContractState(resultSet.getInt("state"));
            contract.setRenterNickname(resultSet.getString("renterNickname"));
            contract.setTenantNickname(resultSet.getString("tenantNickname"));

        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return contract;
    }

    @Override
    public void setContrattoArchiviato(contractBean bean) throws SQLException, transactionError {

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");
        dBConnection.setAutoCommit(false);
        
        PreparedStatement preparedStatement = dBConnection.prepareStatement("INSERT INTO FiledContract (contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported) SELECT contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported FROM ActiveContract WHERE contractId = ?; DELETE FROM ActiveContract WHERE contractId = ?;");
        preparedStatement.setInt(1, bean.getContractId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        PreparedStatement preparedStatement1 = dBConnection.prepareStatement("UPDATE PaymentClaim SET claimState = 3 WHERE contractID = ?");
        preparedStatement1.setInt(1,  bean.getContractId());
        preparedStatement1.executeUpdate();
        preparedStatement1.close();

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
    public void setContrattoSegnalato(contractBean bean) throws SQLException, transactionError {

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");
        dBConnection.setAutoCommit(false);
        
        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE Contract SET claimReported = 1 WHERE contractId = ?");
        preparedStatement.setInt(1, bean.getContractId());
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
}