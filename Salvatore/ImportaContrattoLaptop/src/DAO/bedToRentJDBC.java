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
    private static bedToRentJDBC instance = null;
    
    public static bedToRentJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new bedToRentJDBC();
        return instance;
    }
 
    private bedToRentJDBC() throws SQLException{
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
                        resultSet.getInt("roomID"),
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
    public void bedSetNewAvaiabilityDate(int bedID, String date1, String date2, String date3, String date4) throws SQLException{
        
        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bedID);
        preparedStatement.setString(2, date1);
        preparedStatement.setString(3, date2);

        String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, bedID);
        preparedStatement1.setString(2, date3);
        preparedStatement1.setString(3, date4);

        String query2 = "DELETE FROM avaiabilityCalendar WHERE bedID = ? and startAvaiability = ? and endAvaiability = ? and type = 'bed'";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, bedID);
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
    public LinkedList<String> checkDate(int bedID, String startDate, String endDate) throws SQLException{
        
        LinkedList<String> returnList = new LinkedList<>();

        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE bedID = ? and ? >= startAvaiability and endAvaiability >= ? and type = 'bed'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, bedID);
                preparedStatement.setString(2, startDate);
                preparedStatement.setString(3, endDate);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() == false) {
                    resultSet.close();
                    preparedStatement.close();
                    return returnList;
                }

                returnList.add(resultSet.getString("startAvaiability"));
                returnList.add(resultSet.getString("endAvaiability"));
                resultSet.close();
                preparedStatement.close();
                return returnList;
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
                    resultSet.getInt("roomID"),
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
