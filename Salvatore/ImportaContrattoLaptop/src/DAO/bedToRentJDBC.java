/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import Entity.bedToRent;
import Entity.rentable;
import Entity.rentableFactory;
import java.sql.Connection;

/**
 *
 * @author root
 */
public class bedToRentJDBC implements bedToRentDAO {
    
    Connection connection = null;
 
    public bedToRentJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }
    
    @Override
    public List<rentable> bedListByRenter(String renterNickname)  throws SQLException {
        
        LinkedList<rentable> bedListRenter = new LinkedList<>();
        
        String query = "select * from bedToRent where renterNickname = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, renterNickname);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    rentableFactory factory = new rentableFactory();
                    rentable rentableObject = factory.getRentable(
                        resultSet.getInt("ID"),
                        resultSet.getInt("aptID"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("image"),
                        "bed");
                    bedListRenter.add(rentableObject);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return bedListRenter;
    }
    
    @Override
    public List<bedToRent> bedListByRoom(int roomID)  throws SQLException {
        
        List<bedToRent> bedListRoom = new LinkedList<bedToRent>();
        
        String query = "select * from bedToRent where roomID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(roomID));
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    bedToRent bed = new bedToRent(resultSet.getInt("ID"),
                    resultSet.getInt("aptID"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("image")
                    );
                    bedListRoom.add(bed);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return bedListRoom;
    }
    
}
