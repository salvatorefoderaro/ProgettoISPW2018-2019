package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.rentableBean;
import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.Entity.Enum.TypeOfUser;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.io.IOException;
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
    public userBean renterLogin(userBean sessionBean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

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

    @Override
    public userBean getTenant(rentableBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

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
