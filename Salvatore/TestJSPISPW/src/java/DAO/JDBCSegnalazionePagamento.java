/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java.DAO;

 
import Bean.SegnalazionePagamentoBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import DAO.JDBCContratto;
import DAO.JDBCLocatario;
import DAO.SegnalazionePagamentoDAO;
import DAO.databaseConnection;
import Entity.SegnalazionePagamento;
//import Bean.BeanTest;
 
public class JDBCSegnalazionePagamento implements SegnalazionePagamentoDAO {
    
    Connection connection = null;
    
    public JDBCSegnalazionePagamento() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public List<SegnalazionePagamento> getSegnalazioniPagamento(String userNickname, String type)  throws SQLException {
        System.out.println("Il nome utente è: " + userNickname);
        DAO.JDBCContratto jdbcContratto = null;
        jdbcContratto = new DAO.JDBCContratto();

        DAO.JDBCLocatario jdbcLocatario = null;
        jdbcLocatario = new DAO.JDBCLocatario();
        
        List<SegnalazionePagamento> listaSegnalazioni = new LinkedList<SegnalazionePagamento>();
            String query;
            if ("Locatario".equals(type)){
                query = "select * from paymentClaim where claimNotified = 0 and tenantNickname = ?";
            }
            
            else {
                query = "select * from paymentClaim where claimNotified = 0 and renterNickname = ?";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userNickname);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    SegnalazionePagamento segnalazione = new SegnalazionePagamento(
                    Integer.parseInt(resultSet.getString("claimId")),
                    jdbcContratto.getContratto(Integer.parseInt(resultSet.getString("contractId"))),
                    resultSet.getString("renterNickname"),
                    jdbcLocatario.getLocatario(resultSet.getString("tenantNickname")),
                    Integer.parseInt(resultSet.getString("claimNumber")),
                    resultSet.getString("claimDeadline"),
                    Integer.parseInt(resultSet.getString("claimState")),
                    Integer.parseInt(resultSet.getString("claimNotified"))
                    );
                    listaSegnalazioni.add(segnalazione);
                }
                resultSet.close();
                preparedStatement.close();
                 
            
            return listaSegnalazioni;
    }
    
    @Override
    public SegnalazionePagamento getSegnalazionePagamento(int ID)  throws SQLException{
        
        DAO.JDBCContratto jdbcContratto = new JDBCContratto();
        DAO.JDBCLocatario jdbcLocatario = new JDBCLocatario();
        
        SegnalazionePagamento segnalazione = null;
        String query = "select * from paymentClaim where contractId = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, ID);

                ResultSet resultSet = preparedStatement.executeQuery();
                // statement.setString(userId, userID);
                while(resultSet.next()){
                    segnalazione = new SegnalazionePagamento(
                    Integer.parseInt(resultSet.getString("claimId")),
                    jdbcContratto.getContratto(Integer.parseInt(resultSet.getString("contractId"))),
                    resultSet.getString("renterNickname"),
                    jdbcLocatario.getLocatario(resultSet.getString("tenantNickname")),
                    Integer.parseInt(resultSet.getString("claimNumber")),
                    resultSet.getString("claimDeadline"),
                    Integer.parseInt(resultSet.getString("claimState")),
                    Integer.parseInt(resultSet.getString("claimNotified"))
                    );
                }
                resultSet.close();
                preparedStatement.close();
                 
            return segnalazione;
    }

    @Override
    public void incrementaNumeroSegnalazione(int ID) throws SQLException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE paymentClaim SET claimNumber = claimNumber + 1, claimState = 0, claimDeadline = DATE_ADD(CURDATE(), interval 14 day)  WHERE claimId = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    @Override
    public void createSegnalazionePagamento(SegnalazionePagamentoBean bean) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO paymentClaim (contractId, renterNickname, tenantNickname, claimNumber, claimDeadline, claimState, claimNotified) VALUES (?, ?, ?, 1, ?, 0, 0)");
            preparedStatement.setString(1,  Integer.toString(bean.getContractId()));
            preparedStatement.setString(2,  bean.getRenterNickname());
            preparedStatement.setString(3,  bean.getTenantNickname());
            preparedStatement.setString(4,  bean.getClaimDeadline());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void setSegnalazionePagata(int ID)  throws SQLException{
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE paymentClaim SET claimState = 4  WHERE claimId = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    @Override
    public void setSegnalazionePagamentoArchiviata(int ID) throws SQLException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE paymentClaim SET claimState = 2 WHERE claimId = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void setSegnalazionePagamentoNotificata(int ID) throws SQLException {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE paymentClaim SET claimNotified = 1 WHERE claimId = ?");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    @Override
    public void checkSegnalazionePagamentoData() throws SQLException{
        try {
        PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE paymenyClaim SET claimState = 1 WHERE claimDeadline < CURDATE() and claimNumber != 3");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE paymentClaim SET claimState = 2 WHERE claimDeadline < CURDATE() and claimNumber = 3 and claimState != 3");
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