package it.uniroma2.ispw.fersa.Test.testSetPaymentClaimPayed;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class afterTest {
    /**
     * pulisce l'ambiente per l'esecuzione del test
     * @throws SQLException
     */
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
    public void clear() throws SQLException, IOException {
        clear_table();

    }
}
