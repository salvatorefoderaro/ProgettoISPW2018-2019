/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.renterBean;
import Boundary.testException;
import Entity.Locatario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
 
 
public class renterJDBC implements renterDAO {
    
    Connection connection = null;
    private static renterJDBC instance = null;
    
    public static renterJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new renterJDBC();
        return instance;
    }

    public renterBean login(String username, String password) throws SQLException, testException {

        renterBean renter = new renterBean();
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from renter where renterNickname = ? and renterPassword = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false){
            resultSet.close();
            preparedStatement.close();
            throw new testException("Errore! Nessun utente associato al nickname indicato!");
        } else {
            renter.setNickname(resultSet.getString("renterNickname"));
            renter.setID(resultSet.getInt("renterID"));
            renter.setCF(resultSet.getString("renterCF"));
            resultSet.close();
            preparedStatement.close();
            return renter;
        }

    };


    private renterJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
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