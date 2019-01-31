package DAO;

import Bean.userSessionBean;
import Entity.TypeOfUser;
import Exceptions.emptyResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userJDBC implements userDAO {

    Connection connection = null;
    private static userJDBC instance = null;

    public static userJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
            instance = new userJDBC(type);
        return instance;
    }

    private userJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        }
        if(type == "admin"){
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    @Override
    public userSessionBean login(userSessionBean bean) throws SQLException, emptyResult {

        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from RentingUser WHERE nickname = ? and password = SHA2(?, 256)");
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
            return loggedUser;
        }
    }

    @Override
    public void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE RentingUser SET paymentClaim = paymentClaim + 1 WHERE id = ?");
        preparedStatement.setInt(1, session.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public userSessionBean getTenant(userSessionBean session) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from RentingUser where nickname = ?");
        preparedStatement.setString(1, session.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        userSessionBean tenant = null;
        while(resultSet.next()){
            tenant = new userSessionBean(resultSet.getString("nickname"), resultSet.getInt("id"), TypeOfUser.TENANT, resultSet.getInt("paymentClaim"), "");
        }
        resultSet.close();
        preparedStatement.close();
        return tenant;
    }

}
