/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.contractBean;
import Bean.userSessionBean;
import Exceptions.emptyResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
 
public class contractJDBC implements contractDAO {
 
    private Connection connection = null;
    private static contractJDBC instance = null;
    
    public static contractJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new contractJDBC(type);
        return instance;
    }
 
    private contractJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        }
        if(type == "admin"){
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection() { return connection; }

    @Override
    public List<contractBean> getContracts(userSessionBean user) throws SQLException, emptyResult {

        List<contractBean> listBean = new LinkedList<>();
        String query = "select contractID, tenantNickname, renterNickname from Contract where renterNickname = ? and claimReported = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
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
            return listBean;
        }
    }
    
    @Override
    public contractBean getContract(contractBean bean)  throws SQLException{
        String query = "select tenantNickname, renterNickname, state from Contract where contractID = ? and claimReported = 0";
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
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
                return contract;
    }

    @Override
    public void setContrattoArchiviato(contractBean bean)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO FiledContract (contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported) SELECT contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported FROM ActiveContract WHERE contractId = ?; DELETE FROM ActiveContract WHERE contractId = ?;");
            preparedStatement.setInt(1, bean.getContractId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE PaymentClaim SET claimState = 3 WHERE contractID = ?");
            preparedStatement1.setInt(1,  bean.getContractId());
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            
    }
    
    @Override
    public void setContrattoSegnalato(contractBean bean)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Contract SET claimReported = 1 WHERE contractId = ?");
            preparedStatement.setInt(1, bean.getContractId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            // TODO Auto-generated catch block
    }
    
    public void closeConnection(){
        try {
              if (connection != null) {
                  connection.close();
              }
            } catch (Exception e) { 
            }
    }
}