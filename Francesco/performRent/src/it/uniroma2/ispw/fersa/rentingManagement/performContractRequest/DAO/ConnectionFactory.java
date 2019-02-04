package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory istance;

    private static String CONFIG_PATH = "./config/db.config";

    private static String USER;

    private static String PASS;

    private static String DB_URL;

    private static String DRIVER_CLASS_NAME;

    protected ConnectionFactory() throws ConfigFileException, ConfigException {
        try {
            BufferedReader config = new BufferedReader(new FileReader(CONFIG_PATH));

            Properties properties = new Properties();
            properties.load(config);
            USER = properties.getProperty("Username");
            PASS = properties.getProperty("Password");
            DRIVER_CLASS_NAME = properties.getProperty("Driver_class_name");
            String database = properties.getProperty("Database");
            String database_address = properties.getProperty("Database_address");
            String database_port = properties.getProperty("Database_port");
            String database_name = properties.getProperty("Database_name");

            if (USER == null | DRIVER_CLASS_NAME == null | database == null | database_address == null | database_port == null | database_name == null)
                throw new ConfigException();


            DB_URL = "jdbc:" + database + "://" + database_address + ":" + database_port + "/" + database_name;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConfigFileException();
        }

    }

    public static synchronized ConnectionFactory getInstance() throws ConfigFileException, ConfigException{
        if (istance == null) {
            istance = new ConnectionFactory();
        }
        return istance;
    }

    public Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_CLASS_NAME);
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
