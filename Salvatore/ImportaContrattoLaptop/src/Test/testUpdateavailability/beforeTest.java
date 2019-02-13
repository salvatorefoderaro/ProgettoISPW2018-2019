package Test.testUpdateavailability;

import Bean.userBean;
import DAO.Configuration.readDBConf;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class beforeTest {

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
    public void insert() throws IOException, SQLException, dbConfigMissing, emptyResult {
        clear_table();
        userBean testBean = new userBean();

        insertTable(1);
        insertTable(5);
        insertTable(6);

        testBean.setNickname("francesco");
        controllerSingleton.getInstance().getRentableFromUser(testBean);

    }
}
