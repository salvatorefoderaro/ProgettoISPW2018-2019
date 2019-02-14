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


class JUnity {
    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Login")
    void testLogin() throws SQLException, emptyResult {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void insertTable(int type) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES (?, '2019-01-01', '2019-12-31')");
        preparedStatement.setInt(1, type);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Test
    @DisplayName("Populate DB")
    void populateDB() throws IOException, SQLException {
        insertTable(1);
        insertTable(5);
        insertTable(6);
    }

    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Date")
    void testDate() throws SQLException, dbConfigMissing, IOException {

        // Codice mezzo funziona

        File fBlob = new File ("src/it/uniroma2/ispw/fersa/DAO/Configuration/test.pdf");
        byte[] pdfData = new byte[(int) fBlob.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(fBlob));
        dis.readFully(pdfData);  // read from file into byte[] array


        Connection   dBConnection = DriverManager.getConnection(readDBConf.getDBConf("admin"));

        String query = "INSERT INTO testFile(name, file) VALUES ('test', ?)";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setBytes(1, pdfData);
        preparedStatement.executeUpdate();
        preparedStatement.close();


    }


}
