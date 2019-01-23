/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.aptToRent;
import Entity.bedToRent;
import Entity.rentable;
import Entity.rentableFactory;
import Entity.roomToRent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author root
 */
public class aptToRentJDBC implements aptToRentDAO {
    
    Connection connection = null;
 
    public aptToRentJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }
    
    @Override
    public List<rentable> aptListByRenter(String renterNickname)  throws SQLException {
        
        List<rentable> aptListRenter = new LinkedList<>();
        roomToRentJDBC RoomList = new roomToRentJDBC();
        
        String query = "select * from aptToRent where renterNickname = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, renterNickname);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    rentableFactory factory = new rentableFactory();
                    rentable rentableObject = factory.getRentable(
                            resultSet.getInt("ID"),
                            resultSet.getInt("ID"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("image"),
                            "apt");
                    aptListRenter.add(rentableObject);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return aptListRenter;
    }
}
