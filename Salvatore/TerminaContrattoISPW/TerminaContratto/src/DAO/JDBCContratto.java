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
 
public class JDBCContratto implements ContrattoDAO {
 
    Connection connection = null;
 
    public JDBCContratto() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public List<Contratto> getContratti(int ID)  throws SQLException{
        
        
        List<Contratto> listaContratti = new LinkedList<>();
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
                 
            return listaContratti;
    }
    
    @Override
    public Contratto getContratto(int ID)  throws SQLException{
        Contratto contratto = null;
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
            return contratto;
    }

    @Override
    public void setContrattoArchiviato(int ID)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Contratto SET StatoContratto = 0 WHERE IDContratto = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 3 WHERE IDContratto = ?");
            preparedStatement1.setString(1,  Integer.toString(ID));
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            
    }
    
    @Override
    public void setContrattoSegnalato(int ID)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE Contratto SET StatoContratto = 2 WHERE IDContratto = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            // TODO Auto-generated catch block
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