package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.contractBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.DAO.Configuration.transactionConnection;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;
import it.uniroma2.ispw.fersa.Exceptions.transactionError;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class contractJDBC implements contractDAO {

    public static contractJDBC getInstance() {
        return trueInstance.instance;
    }

    private static class trueInstance {
        private final static contractJDBC instance = new contractJDBC();
    }

    private contractJDBC(){ }

    @Override
    public boolean hasBeenOnApt(String nickname, int aptID) throws SQLException, dbConfigMissing, emptyResult {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }
        String query =  "Select contractID from Contract  WHERE tenantNickname = ? and aptToRentId = ?";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, nickname);
        preparedStatement.setInt(2, aptID);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Nessuna risorsa associata al locatore!");
        }
        return true;
    }

    @Override
    public boolean hasActiveContracts(String nickname) throws SQLException, dbConfigMissing, emptyResult {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }
        String query =  "Select contractID from Contract WHERE tenantNickname = ? or renterNickname = ? and state='Active'";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, nickname);
        preparedStatement.setString(2, nickname);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Nessuna risorsa associata al locatore!");
        }
        return true;
    }


    @Override
    public void createContract(contractBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection dBConnection = transactionConnection.getConnection();

        String query = "";
        switch(bean.getRentableType()) {
            case APARTMENT:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (?, null, null, ?, ?, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;

            case BED:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (null, null, ?, ?, ?, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;

            case ROOM:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (null, ?, null, ?, ?, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getRentableId());
        preparedStatement.setString(2, bean.getRentableType().getType());
        preparedStatement.setInt(3, bean.getContractType().ID);
        preparedStatement.setString(4, bean.getTenantNickname());
        preparedStatement.setString(5, bean.getRenterNickname());
        preparedStatement.setString(6, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(7, bean.getInitDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(8, bean.getTerminationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(9, bean.getTenantName());
        preparedStatement.setString(10, bean.getTenantSurname());
        preparedStatement.setString(11, bean.getTenantSurname());
        preparedStatement.setString(12, bean.getTenantAddress());
        preparedStatement.setString(13, bean.getRenterName());
        preparedStatement.setString(14, bean.getRenterSurname());
        preparedStatement.setString(15, bean.getRenterCF());
        preparedStatement.setString(16, bean.getRenterAddress());
        preparedStatement.setInt(17, bean.getNetPrice());
        preparedStatement.setInt(18, bean.getDeposito());
        preparedStatement.setInt(19, bean.getGrossPrice());
        preparedStatement.executeUpdate();
        preparedStatement.close();

        if (bean.getJDBCcommit()){
            try {
                dBConnection.commit();
                transactionConnection.closeConnection();
            } catch (SQLException e){
                dBConnection.rollback();
                transactionConnection.closeConnection();
                throw new transactionError("");
            }
        }
    }
}