package Test.testUpdateavailability;

import DAO.Configuration.readDBConf;
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
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM AvailabilityCalendar");
        preparedStatement.executeUpdate();
    }



    @Test
    public void clear() throws SQLException, IOException {
        clear_table();
    }
}
