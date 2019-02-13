package DAO.Configuration;

import Exceptions.dbConfigMissing;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Utilizzo una connessione unica per le transazioni "multi DAO". Da utilizzare con cautela

public class transactionConnection {

    private static transactionConnection istanza = null;
    private static Connection dbConnection = null;

    private transactionConnection() throws dbConfigMissing, SQLException {
        try {
            dbConnection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dbConnection.setAutoCommit(false);
    }

    public static synchronized Connection getConnection() throws dbConfigMissing, SQLException {
        if (dbConnection == null) {
            istanza = new transactionConnection();
        }
        return dbConnection;
    }

    public static synchronized void closeConnection() throws SQLException {
        dbConnection.close();
        dbConnection = null;
    }
}
