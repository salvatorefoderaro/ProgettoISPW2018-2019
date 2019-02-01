package DAO;

import Bean.rentableBean;
import Bean.userBean;
import Entity.TypeOfUser;
import Exceptions.emptyResult;

import java.sql.*;

public class userJDBC {

    private Connection connection;
    private static userJDBC instance;

    public static userJDBC getInstance()  throws SQLException {
        if (instance == null)
            instance = new userJDBC();
        return instance;
    }

    private userJDBC(){
    }

    public userBean renterLogin(userBean sessionBean) throws SQLException, emptyResult {

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * from RentingUser WHERE nickname = ? and password = SHA2(?, 256) and type ='RENTER'");
        preparedStatement.setString(1, sessionBean.getNickname());
        preparedStatement.setString(2, sessionBean.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            resultSet.next();
            userBean user = new userBean();
            user.setID(resultSet.getInt("id"));
            user.setNickname(resultSet.getString("nickname"));
            user.setTypeUSer(TypeOfUser.RENTER);
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();

            return user;
        }
    }

    public userBean getTenant(rentableBean bean) throws SQLException, emptyResult {

        Connection dBConnection = DriverManager.getConnection("jdbc:mysql://localhost:8000/RentingManagement?user=root&password=");

        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * from RentingUser where nickname = ? and type = 'TENANT'");
        preparedStatement.setString(1, bean.getTenantNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            resultSet.next();
            userBean tenant = new userBean();
            tenant.setID(resultSet.getInt("id"));
            tenant.setNickname(resultSet.getString("nickname"));
            tenant.setClaimNumber(resultSet.getInt("paymentClaim"));
            tenant.setTypeUSer(TypeOfUser.TENANT);
            resultSet.close();
            preparedStatement.close();
            dBConnection.close();

            return tenant;
        }
    }
}
