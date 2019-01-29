/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.userSessionBean;
import Entity.Tenant;
import Entity.TypeOfUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class tenantJDBC implements tenantDAO {
    
    private Connection connection = null;
    private static tenantJDBC instance = null;
    
    public static tenantJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new tenantJDBC(type);
        return instance;
    }
 
    private tenantJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        }
        if(type == "admin"){
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection() { return connection; }

    @Override
    public void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Locatario SET SollecitiPagamento = SollecitiPagamento + 1 WHERE IDLocatario = ?");
            preparedStatement.setInt(1, session.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();     
}

    @Override
    public userSessionBean getLocatario(userSessionBean session) throws SQLException {
        Tenant locatario = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, session.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        userSessionBean tenant = null;
        while(resultSet.next()){
            tenant = new userSessionBean(resultSet.getString("tenantNickname"), resultSet.getInt("tenantID"), TypeOfUser.TENANT, resultSet.getInt("tenantPaymentClaimNumber"), "");
        }
        resultSet.close();
        preparedStatement.close();    

        return tenant;
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