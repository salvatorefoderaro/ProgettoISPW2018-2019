package DAO;

import Bean.userSessionBean;
import Entity.TypeOfUser;
import Exceptions.dbConfigMissing;
import Exceptions.emptyResult;

import java.sql.*;

public class userJDBC implements userDAO {

    private static userJDBC instance = null;

    public static userJDBC getInstance() {
        if (instance == null)
            instance = new userJDBC();
        return instance;
    }

    @Override
    public userSessionBean login(userSessionBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
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
                loggedUser = new userSessionBean(resultSet.getString("nickname"), resultSet.getInt("id"), TypeOfUser.getType(resultSet.getString("type")), resultSet.getInt("paymentClaim"), "");
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
            tenant = new userSessionBean(resultSet.getString("nickname"), resultSet.getInt("id"), TypeOfUser.TENANT, resultSet.getInt("paymentClaim"), "");
        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return tenant;
    }

}
