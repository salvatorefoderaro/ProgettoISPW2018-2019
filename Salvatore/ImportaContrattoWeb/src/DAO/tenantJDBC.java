/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Exceptions.emptyResult;
import Entity.Tenant;

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
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection(){  return this.connection; }

    @Override
    public Tenant getLocatario(String tenantNickname) throws SQLException, emptyResult {
        Tenant tenant = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, tenantNickname);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();  
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            resultSet.next();
            tenant = new Tenant(resultSet.getInt("tenantID"), resultSet.getString("tenantNickname"), resultSet.getInt("tenantPaymentClaimNumber"), resultSet.getString("tenantCF"));
            resultSet.close();
            preparedStatement.close();  
            return tenant;
        }
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