package it.uniroma2.ispw.fersa.Test;

import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class blankDB {

    public void clear_table() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PaymentClaim");
        preparedStatement.executeUpdate();
        preparedStatement.close();

        PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE Contract set claimReported = 0");
        preparedStatement1.executeUpdate();
        preparedStatement1.close();

        PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE RentingUser set paymentClaim = 0");
        preparedStatement2.executeUpdate();
        preparedStatement2.close();
    }

    @Test
    @DisplayName("Clear Database")
    public void clearDatabase() throws SQLException, IOException {

        clear_table();


    }
}