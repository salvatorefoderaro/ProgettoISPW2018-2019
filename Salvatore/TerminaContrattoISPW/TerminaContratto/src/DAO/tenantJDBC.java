/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Locatario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
 
public class tenantJDBC implements tenantDAO {
    
    Connection connection = null;
 
    public tenantJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }


    @Override
    public void incrementaSollecitiPagamento(int ID)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Locatario SET SollecitiPagamento = SollecitiPagamento + 1 WHERE IDLocatario = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();     
}

    @Override
    public Locatario getLocatario(String tenantNickname) throws SQLException {
        Locatario locatario = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, tenantNickname);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            locatario = new Locatario(Integer.parseInt(resultSet.getString("tenantId")), resultSet.getString("tenantNickname"),
            Integer.parseInt(resultSet.getString("tenantPaymentClaimNumber")));
        }
        resultSet.close();
        preparedStatement.close();    

        return locatario;
    }
    
    public void closeConnection(){
        try {
            if (connection != null) {
                connection.close();
            }
            } catch (Exception e) { 
              //do nothing
            }
    }
}