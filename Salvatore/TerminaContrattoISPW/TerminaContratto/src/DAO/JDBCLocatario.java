/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Entity.Contratto;
import Entity.Locatario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
 
import Entity.SegnalazionePagamento;
//import Bean.BeanTest;
import javafx.fxml.FXMLLoader;
 
public class JDBCLocatario implements LocatarioDAO {
    
 
    Connection connection = null;
 
    public JDBCLocatario() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }


    @Override
    public void incrementaSollecitiPagamento(int ID)  throws SQLException{
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Locatario SET SollecitiPagamento = SollecitiPagamento + 1 WHERE IDLocatario = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();     
            } catch (SQLException e) {
                e.printStackTrace();
            }
}

    @Override
    public Locatario getLocatario(int ID) throws SQLException {
        Locatario locatario = null;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * from Locatario where IDLocatario = ?");
            preparedStatement.setString(1, Integer.toString(ID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                locatario = new Locatario(Integer.parseInt(resultSet.getString("IDLocatario")),
                Integer.parseInt(resultSet.getString("SollecitiPagamento")));
            }
            resultSet.close();
            preparedStatement.close();    
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
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