/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Boundary.testException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import Entity.rentable;
import Entity.rentableFactory;
import Entity.roomToRent;
import java.sql.Connection;

/**
 *
 * @author root
 */
public class roomToRentJDBC implements roomToRentDAO {
    
    private Connection connection;
    private static roomToRentJDBC instance = null;
    
    public static roomToRentJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new roomToRentJDBC();
        return instance;
    }
 
    private roomToRentJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }
    
    @Override
    public List<roomToRent> roomListByApartment(int apartmentID)  throws SQLException {
        
        List<roomToRent> roomListApartment = new LinkedList<>();
        bedToRentJDBC bedList = bedToRentJDBC.getInstance();
        
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
    public LinkedList<String> checkDate(int roomID, String startDate, String endDate) throws SQLException, testException {
        
        LinkedList<String> returnList = new LinkedList<>();

        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE roomID = ? and ? >= startAvaiability and endAvaiability >= ? and type = 'room'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, roomID);
                preparedStatement.setString(2, startDate);
                preparedStatement.setString(3, endDate);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() == false) {
                    resultSet.close();
                    preparedStatement.close();
                    throw new testException("La risorsa non è disponibile per la data indicata!");
                }

                returnList.add(resultSet.getString("startAvaiability"));
                returnList.add(resultSet.getString("endAvaiability"));
                resultSet.close();
                preparedStatement.close();
                return returnList;
    }  
    
    @Override
    public void roomSetNewAvaiabilityDate(int roomID, String date1, String date2, String date3, String date4) throws SQLException{
        
        
        
        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);
            preparedStatement.setString(2, date1);
            preparedStatement.setString(3, date2);
            
                    String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setInt(1, roomID);
            preparedStatement1.setString(2, date3);
            preparedStatement1.setString(3, date4);
            
           String query2 = "DELETE FROM avaiabilityCalendar WHERE roomID = ? and startAvaiability = ? and endAvaiability = ? and type = 'room'";
         PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setInt(1, roomID);
            preparedStatement2.setString(2, date1);
            preparedStatement2.setString(3, date4);
                     
            int resultSet = preparedStatement.executeUpdate();
            int resultSet1 = preparedStatement1.executeUpdate();
            int resultSet2 = preparedStatement2.executeUpdate();

            preparedStatement.close();     
            preparedStatement1.close();                       
            preparedStatement2.close();                       

    }
    
@Override
    public List<rentable> roomListByRenter(String renterUsername)  throws SQLException {
        
        List<rentable> roomListRenter = new LinkedList<>();
        bedToRentJDBC bedList = bedToRentJDBC.getInstance();
        
            String query = "select * from roomToRent where renterNickname = ?";

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
