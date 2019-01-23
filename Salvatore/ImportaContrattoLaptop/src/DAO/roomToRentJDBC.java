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
import Entity.roomToRent;
import java.sql.Connection;

/**
 *
 * @author root
 */
public class roomToRentJDBC implements roomToRentDAO {
    
        Connection connection = null;
 
    public roomToRentJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }
    
    @Override
    public List<roomToRent> roomListByApartment(int apartmentID)  throws SQLException {
        
        List<roomToRent> roomListApartment = new LinkedList<>();
        bedToRentJDBC bedList = new bedToRentJDBC();
        
        String query = "select * from roomToRent where apartmentID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Integer.toString(apartmentID));
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    roomToRent room = new roomToRent(resultSet.getInt("apartmentID"),
                    resultSet.getInt("ID"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("image"),
                    bedList.bedListByRoom(resultSet.getInt("ID"))
                    );
                    roomListApartment.add(room);
                }
                resultSet.close();
                preparedStatement.close();
                 
            return roomListApartment;
    }
    
@Override
    public List<rentable> roomListByRenter(String renterUsername)  throws SQLException {
        
        List<rentable> roomListRenter = new LinkedList<>();
        bedToRentJDBC bedList = new bedToRentJDBC();
        
            String query = "select * from roomToRent where renterUsername = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, renterUsername);
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    rentableFactory rentableFactory = new rentableFactory();
                    rentable rentableObject = rentableFactory.getRentable(resultSet.getInt("ID"),
                            resultSet.getInt("apartmentID"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("image"),
                            "room" );

                    roomListRenter.add(rentableObject);
                }
                resultSet.close();
                preparedStatement.close();
                 
            
            return roomListRenter;
    }
    
}
