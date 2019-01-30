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

import Bean.rentableBean;
import Bean.renterBean;
import Exceptions.emptyResult;

import java.sql.Connection;

/**
 *
 * @author root
 */
public class bedToRentJDBC implements bedToRentDAO {

    Connection connection = null;
    private static bedToRentJDBC instanceUser = null;
    private static bedToRentJDBC instanceAdmin = null;

    public static bedToRentJDBC getInstance(String type)  throws SQLException {
        if (type == "user"){
            if (instanceUser == null)
                instanceUser = new bedToRentJDBC("user");
            return instanceUser;
        } else {
            if (instanceAdmin == null)
                instanceAdmin = new bedToRentJDBC("admin");
            return instanceAdmin;
        }
    }



    private bedToRentJDBC(String type) throws SQLException{
        if(type == "user") {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public Connection getConnection(){  return this.connection; }

    @Override
    public List<rentableBean> bedListByRenter(renterBean renter)  throws SQLException {

        LinkedList<rentableBean> bedListRenter = new LinkedList<>();

        String query = "select * from bedToRent where renterNickname = ? and ID in (Select bedID from avaiabilityCalendar)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, renter.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            rentableBean rentable = new rentableBean();
            rentable.setID(resultSet.getInt("ID"));
            rentable.setBedID(resultSet.getInt("ID"));
            rentable.setRoomID(resultSet.getInt(("roomID")));
            rentable.setName(resultSet.getString("name"));
            rentable.setDescription(resultSet.getString("description"));
            rentable.setImage(resultSet.getString("image"));
            rentable.setType("apt");
            bedListRenter.add(rentable);
        }
        resultSet.close();
        preparedStatement.close();

        return bedListRenter;
    }

    @Override
    public void bedSetNewAvaiabilityDate(rentableBean bean) throws SQLException{

        String query ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getBedID());
        preparedStatement.setString(2, bean.getNewStartAvaiabilityDate());
        preparedStatement.setString(3, bean.getStartDate());

        String query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
        PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
        preparedStatement1.setInt(1, bean.getBedID());
        preparedStatement1.setString(2, bean.getEndDate());
        preparedStatement1.setString(3, bean.getNewEndAvaiabilityDate());

        String query2 = "DELETE FROM avaiabilityCalendar WHERE bedID = ? and startAvaiability = ? and endAvaiability = ? and type = 'bed'";
        PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
        preparedStatement2.setInt(1, bean.getBedID());
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
    public rentableBean checkDate(rentableBean bean) throws SQLException, emptyResult {

        LinkedList<String> returnList = new LinkedList<>();
        rentableBean resultBean = new rentableBean();

        String query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE bedID = ? and ? >= startAvaiability and endAvaiability >= ? and type = 'bed'";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getBedID());
        preparedStatement.setString(2, bean.getStartDate());
        preparedStatement.setString(3, bean.getEndDate());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new Exceptions.emptyResult("");
        }
        resultSet.next();
        resultBean.setNewStartAvaiabilityDate(resultSet.getString("startAvaiability"));
        resultBean.setNewEndAvaiabilityDate(resultSet.getString("endAvaiability"));
        resultSet.close();
        preparedStatement.close();
        return resultBean;
    }

    @Override
    public List<rentableBean> bedListByRoom(rentableBean bean)  throws SQLException {
        List<rentableBean> bedListRoom = new LinkedList<>();

        String query = "select * from bedToRent where roomID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(bean.getRoomID()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            rentableBean rentable = new rentableBean();
            rentable.setID(resultSet.getInt("ID"));
            rentable.setRoomID(resultSet.getInt(("roomID")));
            rentable.setName(resultSet.getString("name"));
            rentable.setDescription(resultSet.getString("description"));
            rentable.setImage(resultSet.getString("image"));
            rentable.setType("apt");
            bedListRoom.add(rentable);
        }
        resultSet.close();
        preparedStatement.close();

        return bedListRoom;
    }

}