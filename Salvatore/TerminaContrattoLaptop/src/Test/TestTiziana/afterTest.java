package Test.TestTiziana;

import DAO.Configuration.readDBConf;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

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

    public void insertTable(int type) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
        PreparedStatement preparedStatement = connection.prepareStatement("Insert INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES (?, '2019-01-01', '2019-12-31')");
        preparedStatement.setInt(1, type);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Test
    public void clear() throws SQLException, IOException {
        clear_table();
        insertTable(1);
        insertTable(5);
        insertTable(6);
    }
}
