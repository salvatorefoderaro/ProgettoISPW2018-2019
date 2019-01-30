package DAO;

import Bean.rentableBean;
import Bean.renterBean;
import Bean.userBean;
import Entity.Renter;
import Entity.Tenant;
import Entity.TypeOfUser;
import Exceptions.emptyResult;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Entity.TypeOfRentable;

import javax.imageio.ImageIO;

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
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from renter WHERE renterNickname = ? and renterPassword = ?");
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
            user.setID(resultSet.getInt("renterID"));
            user.setNickname(resultSet.getString("renterNickname"));
            user.setCF(resultSet.getString("renterCF"));
            user.setTypeUSer(TypeOfUser.RENTER);
            resultSet.close();
            preparedStatement.close();
            return user;
        }
    }

    public userBean getLocatario(rentableBean bean) throws SQLException, emptyResult {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, bean.getTenantNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            resultSet.next();
            userBean tenant = new userBean();
            tenant.setID(resultSet.getInt("tenantID"));
            tenant.setNickname(resultSet.getString("tenantNickname"));
            tenant.setClaimNumber(resultSet.getInt("tenantPaymanetClaimNumber"));
            tenant.setCF(resultSet.getString("tenantCF"));
            tenant.setTypeUSer(TypeOfUser.TENANT);
            resultSet.close();
            preparedStatement.close();
            return tenant;
        }
    }
}
