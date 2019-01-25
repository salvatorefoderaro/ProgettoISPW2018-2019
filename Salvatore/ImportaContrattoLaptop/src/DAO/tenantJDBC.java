/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Boundary.emptyResultException;
import Entity.Tenant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class tenantJDBC implements tenantDAO {
    
    private Connection connection = null;
    private static tenantJDBC instance = null;
    
    public static tenantJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new tenantJDBC();
        return instance;
    }
 
    private tenantJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public Tenant getLocatario(String tenantNickname) throws SQLException, emptyResultException {
        Tenant tenant = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, tenantNickname);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false){
            resultSet.close();
            preparedStatement.close();  
            throw new emptyResultException("Errore! Nessun utente associato al nickname indicato!");
        } else {
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