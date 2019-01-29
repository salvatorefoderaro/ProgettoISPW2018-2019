/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.rentableBean;
import Bean.renterBean;
import Exceptions.emptyResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.Connection;

/**
 *
 * @author root
 */
public class roomToRentJDBC implements roomToRentDAO {
    
    private Connection connection;
    private static roomToRentJDBC instance = null;
    
    public static roomToRentJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
                instance = new roomToRentJDBC(type);
        return instance;
    }
 
    private roomToRentJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection(){  return this.connection; }

    @Override
    public List<rentableBean> roomListByApartment(rentableBean bean)  throws SQLException {
        
        List<rentableBean> roomListApartment = new LinkedList<>();

        String query = "select * from roomToRent where apartmentID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, bean.getAptID());
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    rentableBean rentable = new rentableBean();
                    rentable.setID(resultSet.getInt("ID"));
                    rentable.setRoomID(resultSet.getInt("ID"));
                    rentable.setAptID(resultSet.getInt(("apartmentID")));
                    rentable.setName(resultSet.getString("name"));
                    rentable.setDescription(resultSet.getString("description"));
                    rentable.setImage(resultSet.getString("image"));
                    rentable.setType("room");
                }
                resultSet.close();
                preparedStatement.close();
                 
            return roomListApartment;
    }   

    
@Override
    public rentableBean checkDate(rentableBean bean) throws SQLException, emptyResult {
        LinkedList<String> returnList = new LinkedList<>();
        rentableBean result = new rentableBean();
        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE roomID = ? and ? >= startAvaiability and endAvaiability >= ? and type = 'room'";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, bean.getID());
                preparedStatement.setString(2, bean.getStartDate());
                preparedStatement.setString(3, bean.getEndDate());
                System.out.println(bean.getStartDate() + " " + bean.getEndDate() + " " + bean.getRoomID());
                
                ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                    result.setNewStartAvaiabilityDate(null);
                    resultSet.close();
                    preparedStatement.close();
                    throw new emptyResult("La risorsa non è disponibile per la data indicata!");
                }
                resultSet.next();

                result.setNewStartAvaiabilityDate(resultSet.getString("startAvaiability"));
                result.setNewEndAvaiabilityDate(resultSet.getString("endAvaiability"));
                resultSet.close();
                preparedStatement.close();
                return result;
    }  
    
    @Override
    public void roomSetNewAvaiabilityDate(rentableBean bean) throws SQLException{


        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bean.getRoomID());
            preparedStatement.setString(2, bean.getNewStartAvaiabilityDate());
            preparedStatement.setString(3, bean.getStartDate());
            
                    String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement1.setInt(1, bean.getRoomID());
            preparedStatement1.setString(2, bean.getEndDate());
            preparedStatement1.setString(3, bean.getNewEndAvaiabilityDate());
            
           String query2 = "DELETE FROM avaiabilityCalendar WHERE roomID = ? and startAvaiability = ? and endAvaiability = ? and type = 'room'";
         PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setInt(1, bean.getRoomID());
            preparedStatement2.setString(2, bean.getNewStartAvaiabilityDate());
            preparedStatement2.setString(3, bean.getNewEndAvaiabilityDate());
                     
            int resultSet = preparedStatement.executeUpdate();
            int resultSet1 = preparedStatement1.executeUpdate();
            int resultSet2 = preparedStatement2.executeUpdate();

            preparedStatement.close();     
            preparedStatement1.close();                       
            preparedStatement2.close();                       

    }

@Override
    public List<rentableBean> roomListByRenter(renterBean renter)  throws SQLException {

        List<rentableBean> roomListRenter = new LinkedList<>();

            String query = "select * from roomToRent where renterNickname = ? and ID in (Select roomID from avaiabilityCalendar)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, renter.getNickname());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                    rentableBean rentable = new rentableBean();
                    rentable.setID(resultSet.getInt("ID"));
                    rentable.setRoomID(resultSet.getInt("ID"));
                    rentable.setName(resultSet.getString("name"));
                    rentable.setDescription(resultSet.getString("description"));
                    rentable.setImage(resultSet.getString("image"));
                    rentable.setType("room");
                    roomListRenter.add(rentable);
                }
                resultSet.close();
                preparedStatement.close();
            return roomListRenter;
    }

}
