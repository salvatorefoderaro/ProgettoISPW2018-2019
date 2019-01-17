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
 
public class JDBCContratto implements ContrattoDAO {
 
    Connection connection = null;
 
    public JDBCContratto(){
        connection = getConnection();
    }
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if(connection == null)
                connection = DriverManager.getConnection("jdbc:mysql://localhost:8000/ISPW?user=root&password=");
 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Contratto> getContratti(int ID) {
        
        
        List<Contratto> listaContratti = new LinkedList<>();
        try {
        String query = "select * from Contratto where StatoContratto = 1";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, ID);

                ResultSet resultSet = preparedStatement.executeQuery();
                // statement.setString(userId, userID);
                while(resultSet.next()){
                    Contratto contratto = new Contratto(Integer.parseInt(resultSet.getString("IDContratto")),
                        Integer.parseInt(resultSet.getString("StatoContratto")),
                    Integer.parseInt(resultSet.getString("IDLocatario")));
                  
                    listaContratti.add(contratto);
                }
                resultSet.close();
                preparedStatement.close();
                 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listaContratti;
    }
    
    @Override
    public Contratto getContratto(int ID) {
        getConnection();
        Contratto contratto = null;
        try {
                String query = "SELECT * from Contratto where IDContratto = ?";
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(ID));
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    contratto = new Contratto(Integer.parseInt(resultSet.getString("IDContratto")),
                    Integer.parseInt(resultSet.getString("StatoContratto")), 
                    Integer.parseInt(resultSet.getString("IDLocatario"))
                    );
                }
                resultSet.close();
                preparedStatement.close();
                 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return contratto;
    }

    @Override
    public void setContrattoArchiviato(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Contratto SET StatoContratto = 0 WHERE IDContratto = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 3 WHERE IDContratto = ?");
            preparedStatement1.setString(1,  Integer.toString(ID));
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void setContrattoSegnalato(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Contratto SET StatoContratto = 2 WHERE IDContratto = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
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