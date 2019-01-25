/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.Contratto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
 
public class contractJDBC implements contractDAO {
 
    private Connection connection = null;
    private static contractJDBC instance = null;
    
    public static contractJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new contractJDBC();
        return instance;
    }
 
    private contractJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public List<Contratto> getContratti(String renterNickname)  throws SQLException{
        
        
        List<Contratto> listaContratti = new LinkedList<>();
        String query = "select * from ActiveContract where renterNickname = ? and reported = 0";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, renterNickname);

                ResultSet resultSet = preparedStatement.executeQuery();
                // statement.setString(userId, userID);
                while(resultSet.next()){
                    Contratto contratto = new Contratto(Integer.parseInt(resultSet.getString("contractId")),
                        Integer.parseInt(resultSet.getString("contractState")),
                    resultSet.getString("tenantNickname"),
                    resultSet.getString("renterNickname"));
                  
                    listaContratti.add(contratto);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return listaContratti;
    }
    
    @Override
    public Contratto getContratto(int ID)  throws SQLException{
        Contratto contratto = null;
                String query = "SELECT * from ActiveContract where contractId = ? and reported = 0";
                PreparedStatement preparedStatement = this.connection.prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(ID));
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    contratto = new Contratto(Integer.parseInt(resultSet.getString("contractId")),
                    Integer.parseInt(resultSet.getString("contractState")),
                    resultSet.getString("tenantNickname"),
                    resultSet.getString("renterNickname"));
                }
                resultSet.close();
                preparedStatement.close();
            return contratto;
    }

    @Override
    public void setContrattoArchiviato(int ID)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO FiledContract (contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported) SELECT contractId,isExpired,initDate,terminationDate,paymentMethod,tenantNickname,renterNickname,tenantCF,renterCF,grossPrice,netPrice,frequencyOfPayment,service, reported FROM ActiveContract WHERE contractId = ?; DELETE FROM ActiveContract WHERE contractId = ?;");
            preparedStatement.setString(1,  Integer.toString(ID));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            PreparedStatement preparedStatement1 = this.connection.prepareStatement("UPDATE paymentClaim SET claimState = 3 WHERE contractId = ?");
            preparedStatement1.setString(1,  Integer.toString(ID));
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            
    }
    
    @Override
    public void setContrattoSegnalato(int ID)  throws SQLException{
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE ActiveContract SET contractState = 1 WHERE contractId = ?");
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
            }
    }
}