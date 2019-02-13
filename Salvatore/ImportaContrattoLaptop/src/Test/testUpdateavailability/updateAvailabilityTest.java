package Test.testUpdateavailability;

import Bean.rentableBean;
import DAO.Configuration.readDBConf;
import DAO.Configuration.transactionConnection;
import Entity.Enum.TypeOfRentable;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;
import Exceptions.transactionError;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.assertEquals;

public class updateAvailabilityTest {

    public int countNewAvailability() throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT COUNT(*) as Counter FROM AvailabilityCalendar");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int result = resultSet.getInt("Counter");
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();
        return result;
    }

    @Test
    public void getContract() throws IOException, SQLException, transactionError, dbConfigMissing, emptyResult {


        rentableBean testBean = new rentableBean();

        testBean.setStartDateRequest("2019-02-01");
        testBean.setEndDateRequest("2019-02-28");
        testBean.setType(TypeOfRentable.ROOM);
        testBean.setID(1);
        testBean.setAptID(1);

        controllerSingleton.getInstance().setNewAvailabilityCalendar(testBean);

        // Commit nel controller disabilitato, deve farlo lui
        transactionConnection.getConnection().commit();
        assertEquals(countNewAvailability(), 6);
        System.out.println("\nDisponibilità aggiornata correttamente!\n");

        try {
            controllerSingleton.getInstance().setNewAvailabilityCalendar(testBean);
            System.out.println("Eccezione non catturata!");
        } catch (emptyResult expectedException) {
            System.out.println("La risorsa non è disponibile per il periodo indicato!");
        }
        transactionConnection.getConnection().commit();
    }
}