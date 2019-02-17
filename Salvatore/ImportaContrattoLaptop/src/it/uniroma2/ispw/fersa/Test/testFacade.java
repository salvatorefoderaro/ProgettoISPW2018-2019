package it.uniroma2.ispw.fersa.Test;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.DAO.Configuration.transactionConnection;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import it.uniroma2.ispw.fersa.Facade.rentingManagementInterface;
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

class testFacade {

    @Test
    @DisplayName("Test Facade")
    void facade() throws SQLException, dbConfigMissing, emptyResult {
        rentingManagementInterface facade = new rentingManagementInterface();
        System.out.println(facade.hasActiveContracts("Francesco"));
        facade.getEquippedApartments("Francesco");
        facade.hasBeenHere("Giuseppe", 10);
    }
}
