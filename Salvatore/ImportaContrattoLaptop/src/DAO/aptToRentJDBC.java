/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entity.rentable;
import Entity.rentableFactory;
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
    private static aptToRentJDBC instance = null;
    
    public static aptToRentJDBC getInstance()  throws SQLException {
        if (instance == null)
                instance = new aptToRentJDBC();
        return instance;
    }
 
    private aptToRentJDBC() throws SQLException{
        this.connection = databaseConnection.getConnection();
    }

    @Override
    public void aptSetNewAvaiabilityDate(int aptID, String date1, String date2, String date3, String date4) throws SQLException{

        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'room');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, aptID);
        preparedStatement.setString(2, date1);
        preparedStatement.setString(3, date2);

        String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'room');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, aptID);
        preparedStatement1.setString(2, date3);
        preparedStatement1.setString(3, date4);

        String query2 = "DELETE FROM avaiabilityCalendar WHERE aptID = ? and startAvaiability = ? and endAvaiability = ? and type = 'apt'";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, aptID);
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
    public LinkedList<String> checkDate(int aptID, String startDate, String endDate) throws SQLException{
        
        LinkedList<String> returnList = new LinkedList<>();

        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE aptID = ? and ? >= startAvaiability and endAvaiability >= ? and type = 'apt'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, aptID);
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
    public List<rentable> aptListByRenter(String renterNickname)  throws SQLException {
        
        List<rentable> aptListRenter = new LinkedList<>();
        roomToRentJDBC RoomList = roomToRentJDBC.getInstance();
        
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
