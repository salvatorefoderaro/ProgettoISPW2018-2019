/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author root
 */
public class databaseConnection {
    
    private static Connection dbConnection = null;
    
    private databaseConnection() throws SQLException{}

    
        public static Connection getConnection() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(dbConnection == null)
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/ISPW?user=root&password=");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
             
        }         
            return dbConnection;

        }
    }

