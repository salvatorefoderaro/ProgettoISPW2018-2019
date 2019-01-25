/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Boundary.testException;
import Entity.Locatario;
import Entity.Locatore;

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
 
    private renterJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public Locatore getLocatore(String renterNickname, String renterPassword) throws SQLException, testException {
        Locatario locatario = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from renter WHERE renterNickname = ? and renterPassword = ?");
        preparedStatement.setString(1, renterNickname);
        preparedStatement.setString(2, renterPassword);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next() == false){
            resultSet.close();
            preparedStatement.close();
            throw new testException("Errore! Nessun utente associato al nickname indicato!");
        } else {
            Locatore locatore = new Locatore(resultSet.getInt("renterID"), resultSet.getString("renterNickname"), resultSet.getString("renterCF"));
            resultSet.close();
            preparedStatement.close();
            return locatore;
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