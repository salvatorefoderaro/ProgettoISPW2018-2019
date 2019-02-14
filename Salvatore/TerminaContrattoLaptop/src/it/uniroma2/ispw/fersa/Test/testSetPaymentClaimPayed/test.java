package it.uniroma2.ispw.fersa.Test.testSetPaymentClaimPayed;

import it.uniroma2.ispw.fersa.Bean.paymentClaimBean;
import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class test {

    public int getClaimID() throws dbConfigMissing, SQLException {

            Connection dBConnection = null;
            try {
                dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
            } catch (Exception e) {
                throw new dbConfigMissing("");
            }

            PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT id, COUNT(*) AS test from PaymentClaim");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int number = resultSet.getInt("id");

        preparedStatement.close();
        dBConnection.close();

            return number;
    }

    public int isContractClaimed(int ID) throws dbConfigMissing, SQLException {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT claimReported from Contract where contractID = ?");
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int claim = resultSet.getInt("claimReported");

        preparedStatement.close();
        dBConnection.close();

        return claim;
    }

    @Test
    public void getContract() throws IOException, SQLException, transactionError, dbConfigMissing, emptyResult {

        paymentClaimBean test = new paymentClaimBean();
        test.setContractId(8);
        test.setTenantNickname("giuseppe");
        test.setRenterNickname("francesco");

        userSessionBean user = new userSessionBean("francesco", 0, TypeOfUser.RENTER, 0, "");

        controllerSingleton.getInstance().getPaymentClaims(user);
        test.setClaimId(getClaimID());
        controllerSingleton.getInstance().setPaymentClaimPayed(test);
        assertEquals(0, isContractClaimed(8));
    }
}