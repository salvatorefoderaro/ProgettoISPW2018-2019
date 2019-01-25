/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.contractBean;
import Entity.rentable;
import Entity.rentableFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class contractJDBC implements contractDAO {
 
    private Connection connection = null;
    private static contractJDBC instance = null;
    
    public static contractJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new contractJDBC();
        return instance;
    }
 
    private contractJDBC() throws SQLException {
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public void createContract(contractBean bean) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO ActiveContract (isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported) VALUES (0, ?, ?, 0, ?, ?, ?, ?, ?, ?, 0, NULL, false)");
        preparedStatement.setString(1, bean.getInitDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(2, bean.getTerminationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        preparedStatement.setString(3, bean.getTenantNickname());
        preparedStatement.setString(4, bean.getRenterNickname());
        preparedStatement.setString(5, bean.getTenantCF());
        preparedStatement.setString(6, bean.getRenterCF());
        preparedStatement.setInt(7, bean.getGrossPrice());
        preparedStatement.setInt(8, bean.getNetPrice());
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