package DAO;

import Bean.userSessionBean;
import Entity.Locatario;
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

        PreparedStatement preparedStatement = this.connection.prepareStatement("Select * from ((SELECT renterID as ID, renterNickname as Nickname, renterPassword as Password, 0 AS PaymentClaim, renterCF as CF, 'renter' as tableName  FROM renter) UNION (SELECT tenantID as ID, tenantNickname as Nickname, tenantPassword as Password, tenantPaymentClaimNumber as PaymentClaim, tenantCF as CF, 'tenant' as tableName FROM tenant)) AS loginTable where Nickname=? and Password=?");
        preparedStatement.setString(1, bean.getNickname());
        preparedStatement.setString(2, bean.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Errore! Nessun utente associato al nickname indicato!");
        } else {
            userSessionBean loggedUser = null;
            if(resultSet.next()){
            loggedUser = new userSessionBean(resultSet.getString("Nickname"), resultSet.getInt("ID"), TypeOfUser.getType(resultSet.getString("tableName")), resultSet.getInt("paymentClaim"), "", null);
            }
            resultSet.close();
            preparedStatement.close();
            return loggedUser;
        }

    }

    @Override
    public void incrementaSollecitiPagamento(userSessionBean session)  throws SQLException{
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Locatario SET SollecitiPagamento = SollecitiPagamento + 1 WHERE IDLocatario = ?");
        preparedStatement.setInt(1, session.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public userSessionBean getLocatario(userSessionBean session) throws SQLException {
        Locatario locatario = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, session.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        userSessionBean tenant = null;
        while(resultSet.next()){
            tenant = new userSessionBean(resultSet.getString("tenantNickname"), resultSet.getInt("tenantID"), TypeOfUser.TENANT, resultSet.getInt("tenantPaymentClaimNumber"), "", null);
        }
        resultSet.close();
        preparedStatement.close();

        return tenant;
    }

}
