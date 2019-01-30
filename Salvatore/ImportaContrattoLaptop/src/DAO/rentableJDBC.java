package DAO;

import Bean.rentableBean;
import Bean.renterBean;
import Bean.userBean;
import Exceptions.emptyResult;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Entity.TypeOfRentable;

import javax.imageio.ImageIO;

public class rentableJDBC {
    private Connection connection;
    private static rentableJDBC instance;
    public static rentableJDBC getInstance(String type)  throws SQLException {
        if (instance == null)
            instance = new rentableJDBC(type);
        return instance;
    }

    private rentableJDBC(String type) throws SQLException{
        if(type.equals("user")) {
            this.connection = databaseConnection.getConnectionUser();
        } else {
            this.connection = databaseConnection.getConnectionAdmin();
        }
    }

    public rentableBean checkDate(rentableBean bean) throws SQLException, emptyResult {
        LinkedList<String> returnList = new LinkedList<>();
        rentableBean resultBean = new rentableBean();
        TypeOfRentable testEnum = bean.getType1();
        String query = "";
        System.out.println(bean.getStartDate() + " " + bean.getEndDate() + " " + bean.getID());

        switch (bean.getType1()){
            case APARTMENT:
                query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE aptID = ? and CAST(? as Date) >= startAvaiability and endAvaiability >= CAST(? as Date) and type = 'apt'";
                break;

            case BED:
                query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE bedID = ? and CAST(? as Date) >= startAvaiability and endAvaiability >= CAST(? as Date) and type = 'bed'";
                break;

            case ROOM:
                query = "SELECT startAvaiability, endAvaiability FROM avaiabilityCalendar WHERE roomID = ? and CAST(? as Date) >= startAvaiability and endAvaiability >= CAST(? as Date) and type = 'room'";
                break;
        }

        PreparedStatement preparedStatement = databaseConnection.getConnectionUser().prepareStatement(query);
        preparedStatement.setInt(1, bean.getID());
        preparedStatement.setString(2, bean.getStartDate());
        preparedStatement.setString(3, bean.getEndDate());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("La risorsa non è disponibile per la data indicata!");
        }

        resultSet.next();
        resultBean.setNewStartAvaiabilityDate(resultSet.getString("startAvaiability"));
        resultBean.setNewEndAvaiabilityDate(resultSet.getString("endAvaiability"));
        resultSet.close();
        preparedStatement.close();
        return resultBean;
    }

    public void setNewAvaiabilityDate(rentableBean bean){

        String query1 = "", query2 = "", query3 = "";

        switch (bean.getType1()){
            case APARTMENT:
                query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'apt');";
                query2 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (?, NULL, NULL,?,?, 'apt');";
                query3 = "DELETE FROM avaiabilityCalendar WHERE aptID = ? and startAvaiability = ? and endAvaiability = ? and type = 'apt'";
                break;

            case BED:
                query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
                query2 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, NULL, ?,?,?, 'bed');";
                query3 = "DELETE FROM avaiabilityCalendar WHERE bedID = ? and startAvaiability = ? and endAvaiability = ? and type = 'bed'";
                break;

            case ROOM:
                query1 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
                query2 ="INSERT INTO avaiabilityCalendar (`aptID`, `roomID`, `bedID`, `startAvaiability`, `endAvaiability`, `type`) VALUES (NULL, ?, NULL,?,?, 'room');";
                query3 = "DELETE FROM avaiabilityCalendar WHERE roomID = ? and startAvaiability = ? and endAvaiability = ? and type = 'room'";
                break;
        }

        try {
            PreparedStatement preparedStatement = databaseConnection.getConnectionUser().prepareStatement(query1);
            preparedStatement.setInt(1, bean.getID());
            preparedStatement.setString(2, bean.getNewStartAvaiabilityDate());
            preparedStatement.setString(3, bean.getStartDate());

            PreparedStatement preparedStatement1 = databaseConnection.getConnectionUser().prepareStatement(query2);
            preparedStatement1.setInt(1, bean.getID());
            preparedStatement1.setString(2, bean.getEndDate());
            preparedStatement1.setString(3, bean.getNewEndAvaiabilityDate());

            PreparedStatement preparedStatement2 = databaseConnection.getConnectionUser().prepareStatement(query3);
            preparedStatement2.setInt(1, bean.getID());
            preparedStatement2.setString(2, bean.getNewStartAvaiabilityDate());
            preparedStatement2.setString(3, bean.getNewEndAvaiabilityDate());

            int resultSet = preparedStatement.executeUpdate();
            int resultSet1 = preparedStatement1.executeUpdate();
            int resultSet2 = preparedStatement2.executeUpdate();

            preparedStatement.close();
            preparedStatement1.close();
            preparedStatement2.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        }

    public List<rentableBean> rentableListByRenter(userBean renter)  throws SQLException {

        List<rentableBean> aptListRenter = new LinkedList<>();
        String query = "";

        switch (renter.getTypeRequest()){
            case ROOM:
                query = "select * from roomToRent where renterNickname = ? and ID in (Select roomID from avaiabilityCalendar)";
                break;

            case BED:
                query = "select * from bedToRent where renterNickname = ? and ID in (Select bedID from avaiabilityCalendar)";
                break;

            case APARTMENT:
                query = "select * from aptToRent where renterNickname = ? and ID in (Select aptID from avaiabilityCalendar)";
                break;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, renter.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){

            rentableBean rentable = new rentableBean();

            rentable.setID(resultSet.getInt("ID"));

            switch (renter.getTypeRequest()){
                case ROOM:
                    rentable.setRoomID(resultSet.getInt("ID"));
                    break;

                case BED:
                    rentable.setBedID(resultSet.getInt("ID"));
                    break;

                case APARTMENT:
                    rentable.setAptID(resultSet.getInt("ID"));
                    break;
            }

            rentable.setName(resultSet.getString("name"));
            rentable.setDescription(resultSet.getString("description"));
            try {
                rentable.setImage1(ImageIO.read(resultSet.getBinaryStream("image")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            rentable.setType(renter.getTypeRequest());
            aptListRenter.add(rentable);

        }
        resultSet.close();
        preparedStatement.close();

        return aptListRenter;
    }

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
            rentable.setType(TypeOfRentable.BED);
            bedListRoom.add(rentable);
        }
        resultSet.close();
        preparedStatement.close();

        return bedListRoom;
    }

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
            rentable.setType(TypeOfRentable.ROOM);
        }
        resultSet.close();
        preparedStatement.close();

        return roomListApartment;
    }
}
