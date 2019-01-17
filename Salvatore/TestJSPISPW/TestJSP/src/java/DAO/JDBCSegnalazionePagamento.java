/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Bean.SegnalazionePagamentoBean;
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
 
public class JDBCSegnalazionePagamento implements SegnalazionePagamentoDAO {
    
    public JDBCSegnalazionePagamento(){
        this.connection = getConnection();
    }
 
    Connection connection = null;
 
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
    public List<SegnalazionePagamento> getSegnalazioniPagamento(int IDUtente, String type) {
        
        JDBCContratto jdbcContratto = new JDBCContratto();
        JDBCLocatario jdbcLocatario = new JDBCLocatario();
        
        List<SegnalazionePagamento> listaSegnalazioni = new LinkedList<SegnalazionePagamento>();
        try {
            String query;
            if (type == "Locatario"){
                query = "select * from SegnalazioneContratto where Notified = 0 and IDLocatario = ?";
            }
            
            else {
                query = "select * from SegnalazioneContratto where Notified = 0 and IDLocatore = ?";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, IDUtente);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    SegnalazionePagamento segnalazione = new SegnalazionePagamento(
                    Integer.parseInt(resultSet.getString("ID")),
                    jdbcContratto.getContratto(Integer.parseInt(resultSet.getString("IDContratto"))),
                    Integer.parseInt(resultSet.getString("IDLocatore")),
                    jdbcLocatario.getLocatario(Integer.parseInt(resultSet.getString("IDLocatario"))),
                    Integer.parseInt(resultSet.getString("NumeroReclamo")),
                    resultSet.getString("ScadenzaReclamo"),
                    Integer.parseInt(resultSet.getString("Stato")),
                    Integer.parseInt(resultSet.getString("Notified"))
                    );
                    listaSegnalazioni.add(segnalazione);
                }
                resultSet.close();
                preparedStatement.close();
                 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listaSegnalazioni;
    }
    
    @Override
    public SegnalazionePagamento getSegnalazionePagamento(int ID) {
        
        JDBCContratto jdbcContratto = new JDBCContratto();
        JDBCLocatario jdbcLocatario = new JDBCLocatario();
        
        SegnalazionePagamento segnalazione = null;
        try {
        String query = "select * from SegnalazioneContratto where IDContratto = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, ID);

                ResultSet resultSet = preparedStatement.executeQuery();
                // statement.setString(userId, userID);
                while(resultSet.next()){
                    segnalazione = new SegnalazionePagamento(
                    Integer.parseInt(resultSet.getString("ID")),
                    jdbcContratto.getContratto(Integer.parseInt(resultSet.getString("IDContratto"))),
                    Integer.parseInt(resultSet.getString("IDLocatore")),
                    jdbcLocatario.getLocatario(Integer.parseInt(resultSet.getString("IDLocatario"))),
                    Integer.parseInt(resultSet.getString("NumeroReclamo")),
                    resultSet.getString("ScadenzaReclamo"),
                    Integer.parseInt(resultSet.getString("Stato")),
                    Integer.parseInt(resultSet.getString("Notified"))
                    );
                }
                resultSet.close();
                preparedStatement.close();
                 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return segnalazione;
    }

    @Override
    public void incrementaNumeroSegnalazione(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET NumeroReclamo = NumeroReclamo + 1, Stato = 0, ScadenzaReclamo = DATE_ADD(CURDATE(), interval 14 day)  WHERE ID = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    @Override
    public void createSegnalazionePagamento(SegnalazionePagamentoBean bean) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO SegnalazioneContratto (IDContratto, IDLocatore, IDLocatario, NumeroReclamo, ScadenzaReclamo, Stato, Notified) VALUES (?, 10, ?, 1, ?, 0, 0)");
            preparedStatement.setString(1,  Integer.toString(bean.getIDContratto()));
            preparedStatement.setString(2,  Integer.toString(bean.getIDLocatario()));
            preparedStatement.setString(3,  bean.getScadenzaReclamo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void setSegnalazionePagata(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 4  WHERE ID = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    @Override
    public void setSegnalazionePagamentoArchiviata(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 2 WHERE ID = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void setSegnalazionePagamentoNotificata(int ID) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Notified = 1 WHERE ID = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void checkSegnalazionePagamentoData(){
        try {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 1 WHERE ScadenzaReclamo < CURDATE() and NumeroReclamo != 3");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE SegnalazioneContratto SET Stato = 2 WHERE ScadenzaReclamo < CURDATE() and NumeroReclamo = 3 and Stato != 3");
        preparedStatement1.executeUpdate();
        preparedStatement1.close();   
        } catch (SQLException e) {
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