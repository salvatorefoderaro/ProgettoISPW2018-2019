/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
    public List<SegnalazionePagamento> getSegnalazioniPagamento(String userNickname)  throws SQLException {
        contractJDBC jdbcContratto = null;
        jdbcContratto =contractJDBC.getInstance();

        tenantJDBC jdbcLocatario = null;
        jdbcLocatario = new tenantJDBC();
        
        List<SegnalazionePagamento> listaSegnalazioni = new LinkedList<SegnalazionePagamento>();
            String query = "select * from paymentClaim where claimNotified = 0 and tenantNickname = ?";
 
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