package DAO;

import Bean.rentableBean;
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

        switch (bean.getType()){
            case APARTMENT:

                query = "Select startDate, endDate from AvailabilityCalendar WHERE renterFeaturesId IN (SELECT id from RentalFeatures WHERE AptToRentId = ?) ";
                break;

            case BED:
                query = "Select startDate, endDate from AvailabilityCalendar WHERE renterFeaturesId IN (SELECT id from RentalFeatures WHERE BedToRentId = ?) ";
                break;

            case ROOM:
                query = "Select startDate, endDate from AvailabilityCalendar WHERE renterFeaturesId IN (SELECT id from RentalFeatures WHERE RoomToRentId = ?) ";
                break;
        }

        PreparedStatement preparedStatement = databaseConnection.getConnectionUser().prepareStatement(query);
        preparedStatement.setInt(1, bean.getID());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("La risorsa non è disponibile per la data indicata!");
        }

        resultSet.next();
        resultBean.setNewStartAvaiabilityDate(resultSet.getString("startDate"));
        resultBean.setNewEndAvaiabilityDate(resultSet.getString("endDate"));
        resultSet.close();
        preparedStatement.close();
        return resultBean;
    }

    public void setNewAvaiabilityDate(rentableBean bean){

        String query1 = "", query2 = "", query3 = "";

        switch (bean.getType1()){
            case APARTMENT:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE aptToRentId = ?), ?, ?);";
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE aptToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE aptToRentId = ?)";
                break;

            case BED:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE bedToRentId = ?), ?, ?);";
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE bedToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE bedToRentId = ?)";
                break;

            case ROOM:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE roomToRentId = ?), ?, ?);";
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE roomToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE roomToRentId = ?)";
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
                query = "SELECT Room.id, Room.roomId, Room.name, Room.description, Room.image FROM RoomToRent as Room JOIN RentalFeatures as Feature ON Room.id = Feature.roomToRentId AND Feature.id in (Select renterFeaturesId FROM AvailabilityCalendar)";
                break;

            case BED:
                query = "SELECT Bed.id, Bed.roomId, Bed.name, Bed.description, Bed.image FROM BedToRent as Bed JOIN RentalFeatures as Feature ON Bed.id = Feature.bedToRentId AND Feature.id in (Select renterFeaturesId FROM AvailabilityCalendar)";
                break;

            case APARTMENT:
                query = "SELECT Apt.id, Apt.roomId, Apt.name, Apt.description, Apt.image FROM AptToRent as Apt JOIN RentalFeatures as Feature ON Apt.id = Feature.aptToRentId AND Feature.id in (Select renterFeaturesId FROM AvailabilityCalendar)";
                break;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, renter.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){

            rentableBean rentable = new rentableBean();

            rentable.setID(resultSet.getInt("id"));

            switch (renter.getTypeRequest()){
                case ROOM:
                    rentable.setRoomID(resultSet.getInt("id"));
                    break;

                case BED:
                    rentable.setBedID(resultSet.getInt("id"));
                    break;

                case APARTMENT:
                    rentable.setAptID(resultSet.getInt("id"));
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

        String query = "select id, roomId, name, description, image from BedToRent where roomId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(bean.getRoomID()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            rentableBean rentable = new rentableBean();
            rentable.setID(resultSet.getInt("id"));
            rentable.setRoomID(resultSet.getInt(("roomId")));
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

        String query = "select * from RoomToRent where aptId = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getAptID());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            rentableBean rentable = new rentableBean();
            rentable.setID(resultSet.getInt("id"));
            rentable.setRoomID(resultSet.getInt("id"));
            rentable.setAptID(resultSet.getInt(("aptId")));
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
