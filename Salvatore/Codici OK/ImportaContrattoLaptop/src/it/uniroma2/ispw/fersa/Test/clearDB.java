package it.uniroma2.ispw.fersa.Test;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.DAO.Configuration.transactionConnection;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class clearDB {

    public void clear_table() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM AvailabilityCalendar");
        preparedStatement.executeUpdate();
    }

    public void insertTable(int type) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES (?, '2019-01-01', '2019-12-31')");
        preparedStatement.setInt(1, type);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Test
    @DisplayName("Clear Database and insert Renting Availability")
    void clearDatabase() throws SQLException, dbConfigMissing, IOException {

        clear_table();

        insertTable(1);
        insertTable(5);
        insertTable(6);


    }


}
