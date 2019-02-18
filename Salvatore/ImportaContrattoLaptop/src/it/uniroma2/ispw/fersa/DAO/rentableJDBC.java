package it.uniroma2.ispw.fersa.DAO;

import it.uniroma2.ispw.fersa.Bean.availabilityPeriodBean;
import it.uniroma2.ispw.fersa.Bean.rentableBean;
import it.uniroma2.ispw.fersa.Bean.userBean;
import it.uniroma2.ispw.fersa.DAO.Configuration.readDBConf;
import it.uniroma2.ispw.fersa.DAO.Configuration.transactionConnection;
import it.uniroma2.ispw.fersa.Entity.Enum.typeOfRentable;
import it.uniroma2.ispw.fersa.Exceptions.dbConfigMissing;
import it.uniroma2.ispw.fersa.Exceptions.emptyResult;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import it.uniroma2.ispw.fersa.Exceptions.transactionError;

import javax.imageio.ImageIO;

public class rentableJDBC implements rentableDAO{

    public static rentableJDBC getInstance() {
        return rentableJDBC.trueInstance.instance;
    }

    private static class trueInstance {
        private final static rentableJDBC instance = new rentableJDBC();
    }

    private rentableJDBC(){ }

    @Override
    public List<Integer> getEquippedApartments(String renterNickname) throws SQLException, dbConfigMissing, emptyResult {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }
        String query =  "Select DISTINCT aptId FROM RentalFeatures JOIN AptToRent on RentalFeatures.aptId = AptToRent.id WHERE AptToRent.renterNickname = ?";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, renterNickname);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("Nessuna risorsa associata al locatore!");
        }
        List<Integer> listAvailability = new LinkedList<>();
        while(resultSet.next()) {
            listAvailability.add(resultSet.getInt("aptId"));
        }
        return listAvailability;
    }

    @Override
    public List<availabilityPeriodBean> getAvailabilityDateBean(rentableBean bean) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }
        String query = "";

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

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setInt(1, bean.getID());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()){
            resultSet.close();
            preparedStatement.close();
            throw new emptyResult("La risorsa non è disponibile per la data indicata!");
        }
        List<availabilityPeriodBean> listAvailability = new LinkedList<>();
        while(resultSet.next()) {
            availabilityPeriodBean singlePeriod = new availabilityPeriodBean();
            singlePeriod.setStartDate(LocalDate.parse(resultSet.getString("startDate")));
            singlePeriod.setEndDate(LocalDate.parse(resultSet.getString("endDate")));
            listAvailability.add(singlePeriod);
        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return listAvailability;
    }

    @Override
    public void setNewAvaiabilityDate(rentableBean bean) throws SQLException, transactionError, dbConfigMissing {

        Connection connection = transactionConnection.getConnection();

        String query1 = null;
        String query2 = null;
        String query3 = null;

        switch (bean.getType()){
            case APARTMENT:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE aptToRentId = ?), ?, ?);";
                query2 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE aptToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE aptToRentId = ?) AND startDate = ? AND endDate = ?";
                break;

            case BED:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE bedToRentId = ?), ?, ?);";
                query2 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE bedToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE bedToRentId = ?) AND startDate = ? AND endDate = ?";
                break;

            case ROOM:
                query1 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE roomToRentId = ?), ?, ?);";
                query2 ="INSERT INTO AvailabilityCalendar (renterFeaturesId, startDate, endDate) VALUES ((SELECT id FROM RentalFeatures WHERE roomToRentId = ?), ?, ?);";
                query3 = "DELETE FROM AvailabilityCalendar WHERE renterFeaturesId = (SELECT id FROM RentalFeatures WHERE roomToRentId = ?) AND startDate = ? AND endDate = ?";
                break;
        }

        if(!LocalDate.parse(bean.getStartDateRequest()).plusDays(1).equals(LocalDate.parse(bean.getStartDateAvaliable()))) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, bean.getID());
            preparedStatement.setString(2, bean.getStartDateAvaliable());
            preparedStatement.setString(3, bean.getStartDateRequest());
            int resultSet = preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        if(!LocalDate.parse(bean.getEndDateRequest()).minusDays(1).equals(LocalDate.parse(bean.getEndDateAvaliable()))) {

            PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
            preparedStatement1.setInt(1, bean.getID());
            preparedStatement1.setString(2, bean.getEndDateRequest());
            preparedStatement1.setString(3, bean.getEndDateAvaliable());
            int resultSet1 = preparedStatement1.executeUpdate();
            preparedStatement1.close();
        }

        PreparedStatement preparedStatement2 = connection.prepareStatement(query3);
        preparedStatement2.setInt(1, bean.getID());
        preparedStatement2.setString(2, bean.getStartDateAvaliable());
        preparedStatement2.setString(3, bean.getEndDateAvaliable());

        int resultSet2 = preparedStatement2.executeUpdate();
        preparedStatement2.close();

        if (bean.getJDBCcommit()){
            try {
                connection.commit();
                connection.close();
            } catch (SQLException e){
                connection.rollback();
                connection.close();
                throw new transactionError("");
            }
        }
    }

    @Override
    public List<rentableBean> rentableListByRenter(userBean renter) throws SQLException, emptyResult, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

        List<rentableBean> aptListRenter = new LinkedList<>();
        String query = "";

        switch (renter.getTypeRequest()){
            case ROOM:
                query = "SELECT Room.id, Room.name, Room.description, Room.image, Period.startDate, Period.endDate, AptToRent.id as aptId FROM RoomToRent as Room JOIN RentalFeatures as Feature ON Room.id = Feature.roomToRentId JOIN AptToRent on AptToRent.id = Feature.aptId JOIN AvailabilityCalendar as Period on Period.renterFeaturesId = Feature.id WHERE AptToRent.renterNickname = ? AND Feature.id in (Select renterFeaturesId FROM AvailabilityCalendar) GROUP BY Period.renterFeaturesId";
                break;

            case BED:
                query = "SELECT Bed.id, Bed.name, Bed.description, Bed.image, Period.startDate, Bed.roomId, AptToRent.id as aptId, Period.endDate FROM BedToRent as Bed JOIN RentalFeatures as Feature ON Bed.id = Feature.bedToRentId JOIN AptToRent on AptToRent.id = Feature.aptId JOIN AvailabilityCalendar as Period on Period.renterFeaturesId = Feature.id WHERE AptToRent.renterNickname = ? AND Feature.id IN (Select renterFeaturesID FROM AvailabilityCalendar) GROUP BY Period.renterFeaturesId";
                break;

            case APARTMENT:
                query = "SELECT Apt.id, Apt.name, Apt.description, Apt.image, Period.startDate, Period.endDate FROM AptToRent as Apt JOIN RentalFeatures as Feature ON Apt.id = Feature.aptToRentId JOIN AptToRent on AptToRent.id = Feature.aptId JOIN AvailabilityCalendar as Period on Period.renterFeaturesId = Feature.id WHERE AptToRent.renterNickname = ? AND Feature.id IN (Select renterFeaturesID FROM AvailabilityCalendar) GROUP BY Period.renterFeaturesId";
                break;
        }

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, renter.getNickname());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){

            rentableBean rentable = new rentableBean();

            rentable.setID(resultSet.getInt("id"));

            switch (renter.getTypeRequest()){
                case ROOM:
                    rentable.setRoomID(rentable.getID());
                    rentable.setAptID(resultSet.getInt("aptId"));
                    break;

                case BED:
                    rentable.setBedID(rentable.getID());
                    rentable.setRoomID(resultSet.getInt("roomId"));
                    rentable.setAptID(resultSet.getInt("aptId"));
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
            rentable.setListAvailability(getAvailabilityDateBean(rentable));
            aptListRenter.add(rentable);

        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return aptListRenter;
    }

    @Override
    public List<rentableBean> bedListByRoom(rentableBean bean) throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

        List<rentableBean> bedListRoom = new LinkedList<>();

        String query = "select id, roomId, name, description, image from BedToRent where roomId = ?";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(bean.getRoomID()));
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            rentableBean rentable = new rentableBean();
            rentable.setID(resultSet.getInt("id"));
            rentable.setRoomID(resultSet.getInt(("roomId")));
            rentable.setName(resultSet.getString("name"));
            rentable.setDescription(resultSet.getString("description"));
            rentable.setImage(resultSet.getString("image"));
            rentable.setType(typeOfRentable.BED);
            bedListRoom.add(rentable);
        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return bedListRoom;
    }

    @Override
    public List<rentableBean> roomListByApartment(rentableBean bean) throws SQLException, dbConfigMissing {

        Connection dBConnection = null;
        try {
            dBConnection = DriverManager.getConnection(readDBConf.getDBConf("user"));
        } catch (IOException e) {
            throw new dbConfigMissing("");
        }

        List<rentableBean> roomListApartment = new LinkedList<>();

        String query = "select * from RoomToRent where aptId = ?";

        PreparedStatement preparedStatement = dBConnection.prepareStatement(query);
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
            rentable.setType(typeOfRentable.ROOM);
            roomListApartment.add(rentable);
        }
        resultSet.close();
        preparedStatement.close();
        dBConnection.close();

        return roomListApartment;
    }
}