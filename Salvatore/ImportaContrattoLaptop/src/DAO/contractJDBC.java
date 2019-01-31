package DAO;

import Bean.contractBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class contractJDBC implements contractDAO {
 
    private Connection connection = null;
    private static contractJDBC instance = null;

    public static contractJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new contractJDBC(type);
        return instance;
    }
 
    private contractJDBC(String type) throws SQLException {
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection(){  return this.connection; }

    @Override
    // Controllare se va aggiunto il Contract Type ID come variabile
    public void createContract(contractBean bean) throws SQLException {
        System.out.println(bean.getTenantNickname());
        String query = "";
        switch(bean.getRentableType()) {
            case APARTMENT:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (?, null, null, ?, 0, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;

            case BED:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (null, null, ?, ?, 0, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;

            case ROOM:
                query = "INSERT INTO Contract (aptToRentId, roomToRentId, bedToRentId, type, contractTypeId, state, tenantNickname, renterNickname, creationDate, stipulationDate, startDate, endDate, tenantName, tenantSurname, tenantCF, tenantAddress, renterName, renterSurname, renterCF, renterAddress, price, deposit, claimReported, serviceList, grossPrice) VALUES (null, ?, null, ?, 0, 'Active', ?, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, null, ?)";
                break;
        }
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getRentableId());
        preparedStatement.setString(2, bean.getRentableType().getType());
        preparedStatement.setString(3, bean.getTenantNickname());
        preparedStatement.setString(4, bean.getRenterNickname());
        preparedStatement.setString(5, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(6, bean.getInitDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(7, bean.getTerminationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(8, bean.getTenantName());
        preparedStatement.setString(9, bean.getTenantSurname());
        preparedStatement.setString(10, bean.getTenantSurname());
        preparedStatement.setString(11, bean.getTenantAddress());
        preparedStatement.setString(12, bean.getRenterName());
        preparedStatement.setString(13, bean.getRenterSurname());
        preparedStatement.setString(14, bean.getRenterCF());
        preparedStatement.setString(15, bean.getRenterAddress());
        preparedStatement.setInt(16, bean.getNetPrice());
        preparedStatement.setInt(17, bean.getDeposito());
        preparedStatement.setInt(18, bean.getGrossPrice());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void closeConnection(){
        try {
              if (connection != null) {
                  connection.close();
              }
            } catch (Exception e) { 
            }
    }
}