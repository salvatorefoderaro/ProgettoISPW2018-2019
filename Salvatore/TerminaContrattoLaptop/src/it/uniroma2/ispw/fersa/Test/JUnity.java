package it.uniroma2.ispw.fersa.Test;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class JUnity {
    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Login")
    void emptyPaymentClaims() throws SQLException, dbConfigMissing {
        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("DELETE FROM PaymentClaim");
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    @Test
    @DisplayName("it.uniroma2.ispw.fersa.Test Date")
    void testDate() throws SQLException, dbConfigMissing {
        System.out.println(this.getClass().getResource("").getPath());
    }


}
