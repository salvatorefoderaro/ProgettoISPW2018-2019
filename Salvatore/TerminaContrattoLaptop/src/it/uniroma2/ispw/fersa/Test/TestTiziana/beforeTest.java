package it.uniroma2.ispw.fersa.Test.TestTiziana;

import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;


public class beforeTest {

    public void clear_table() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(readDBConf.getDBConf("admin"));
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM AvailabilityCalendar");
            preparedStatement.executeUpdate();
            }



    @Test
    public void insert() throws IOException, SQLException {
        clear_table();
    }
}
