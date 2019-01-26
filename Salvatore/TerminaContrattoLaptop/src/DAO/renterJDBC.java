/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
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
        }
        if(type == "admin"){
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection() { return connection; }

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