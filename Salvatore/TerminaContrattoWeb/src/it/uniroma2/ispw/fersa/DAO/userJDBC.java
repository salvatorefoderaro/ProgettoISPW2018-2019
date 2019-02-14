package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.userSessionBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import java.sql.*;

public class userJDBC implements userDAO {

    public static userJDBC getInstance() {
        return userJDBC.trueInstance.instance;
    }

    private static class trueInstance {
        private final static userJDBC instance = new userJDBC();
    }

    private userJDBC(){ }

    @Override
    public userSessionBean login(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * from RentingUser WHERE nickname = ? and password = SHA2(?, 256)");
        preparedStatement.setString(1, bean.getNickname());
        preparedStatement.setString(2, bean.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            userSessionBean loggedUser = null;
            while(resultSet.next()){
                loggedUser = new userSessionBean(resultSet.getString("nickname"), resultSet.getInt("id"), TypeOfUser.getType(resultSet.getString("type")), resultSet.getInt("paymentClaim"), "", null);
                System.out.println(resultSet.getString("type"));
                System.out.println("Tipo è: " + loggedUser.getUserType());
            }
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();

            return loggedUser;
        }
    }

    @Override
    public void incrementPaymentClaimNumber(userSessionBean session) throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }
        dBConnection.setAutoCommit(false);

        PreparedStatement preparedStatement = dBConnection.prepareStatement("UPDATE RentingUser SET paymentClaim = paymentClaim + 1 WHERE id = ?");
        preparedStatement.setInt(1, session.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        dBConnection.close();
    }

    @Override
    public userSessionBean getTenant(userSessionBean session) throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (Exception e) {
            throw new dbConfigMissing("");
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * from RentingUser where nickname = ?");
        preparedStatement.setString(1, session.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        userSessionBean tenant = null;
        while(resultSet.next()){
            tenant = new userSessionBean(resultSet.getString("nickname"), resultSet.getInt("id"), TypeOfUser.TENANT, resultSet.getInt("paymentClaim"), "", null);
        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return tenant;
    }

}
