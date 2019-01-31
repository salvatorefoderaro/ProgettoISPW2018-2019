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

    private static Connection dbConnectionUser = null;
    private static Connection dbConnectionAdmin = null;

    public databaseConnection(String type) throws SQLException{
        if (type == "admin"){
            DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");
        }

    }


    public static Connection getConnectionUser() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(dbConnectionUser == null)
                dbConnectionUser = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbConnectionUser;
    }

    public static Connection getConnectionAdmin() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(dbConnectionAdmin == null)
                dbConnectionAdmin = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");
            dbConnectionAdmin.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbConnectionAdmin;
    }
}

