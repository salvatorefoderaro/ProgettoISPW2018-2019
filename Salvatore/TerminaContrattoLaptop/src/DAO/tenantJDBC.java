/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.userSessionBean;
import Entity.Locatario;
import Entity.SegnalazionePagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
 
 
public class tenantJDBC implements tenantDAO {
    
    private Connection connection = null;
    private static tenantJDBC instance = null;
    
    public static tenantJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new tenantJDBC();
        return instance;
    }
 
    private tenantJDBC() throws SQLException{
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
    public userSessionBean getLocatario(userSessionBean session) throws SQLException {
        Locatario locatario = null;
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from tenant where tenantNickname = ?");
        preparedStatement.setString(1, session.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        userSessionBean tenant = null;
        while(resultSet.next()){
            tenant = new userSessionBean(resultSet.getString("tenantNickname"), resultSet.getInt("tenantID"), "tenant", resultSet.getInt("tenantPaymentClaimNumber"));
        }
        resultSet.close();
        preparedStatement.close();    

        return tenant;
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