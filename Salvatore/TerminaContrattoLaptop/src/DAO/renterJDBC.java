/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Locatario;
import Entity.SegnalazionePagamento;
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