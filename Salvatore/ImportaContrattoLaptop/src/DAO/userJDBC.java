package DAO;

import Bean.rentableBean;
import Bean.userBean;
import Entity.TypeOfUser;
import Exceptions.emptyResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userJDBC {

    private Connection connection;
    private static userJDBC instance;

    public static userJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
            instance = new userJDBC(type);
        return instance;
    }

    private userJDBC(String type) throws SQLException{
        if(type.equals("user")) {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public userBean getLocatore(userBean sessionBean) throws SQLException, emptyResult {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from RentingUser WHERE nickname = ? and password = SHA2(?, 256) and type ='RENTER'");
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
            return user;
        }
    }

    public userBean getLocatario(rentableBean bean) throws SQLException, emptyResult {
        System.out.println(bean.getTenantNickname());
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from RentingUser where nickname = ? and type = 'TENANT'");
        preparedStatement.setString(1, bean.getTenantNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
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
            return tenant;
        }
    }
}
