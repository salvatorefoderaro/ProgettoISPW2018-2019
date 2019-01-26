/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Exceptions.emptyResult;
import Entity.Tenant;
import Entity.Renter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class renterJDBC implements renterDAO {
    
    Connection connection = null;
    private static renterJDBC instance = null;
    
    public static renterJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new renterJDBC(type);
        return instance;
    }
 
    private renterJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection(){  return this.connection; }

    @Override
    public Renter getLocatore(String renterNickname, String renterPassword) throws SQLException, emptyResult {
        Tenant tenant = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from renter WHERE renterNickname = ? and renterPassword = ?");
        preparedStatement.setString(1, renterNickname);
        preparedStatement.setString(2, renterPassword);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            Renter renter = new Renter(resultSet.getInt("renterID"), resultSet.getString("renterNickname"), resultSet.getString("renterCF"));
            resultSet.close();
            preparedStatement.close();
            return renter;
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