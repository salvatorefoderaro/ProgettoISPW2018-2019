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
 
 
public class renterJDBC implements renterDAO {
    
    Connection connection = null;
    private static renterJDBC instance = null;
    
    public static renterJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new renterJDBC();
        return instance;
    }
 
    private renterJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }



        @Override
    public List<SegnalazionePagamento> getSegnalazioniPagamento(String userNickname)  throws SQLException {
        contractJDBC jdbcContratto = null;
        jdbcContratto = contractJDBC.getInstance();

        tenantJDBC jdbcLocatario = null;
        jdbcLocatario = tenantJDBC.getInstance();
        
        List<SegnalazionePagamento> listaSegnalazioni = new LinkedList<SegnalazionePagamento>();
            String query = "select * from paymentClaim where claimNotified = 0 and renterNickname = ?";
 
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