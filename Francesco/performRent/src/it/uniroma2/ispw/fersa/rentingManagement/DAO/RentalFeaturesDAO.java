package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.DateRange;
import it.uniroma2.ispw.fersa.rentingManagement.entity.RentalFeatures;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalFeaturesDAO {

    protected static RentalFeaturesDAO rentalFeaturesDAO = new RentalFeaturesDAO();


    protected RentalFeaturesDAO(){

    }

    public static RentalFeaturesDAO getInstance() {
        return rentalFeaturesDAO;
    }

    public RentalFeatures getRentalFeatures(int rentalFeaturesId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        RentalFeatures rentalFeatures = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, description, price, deposit FROM RentalFeatures WHERE id = " + rentalFeaturesId;

            ResultSet rs1 = stmt.executeQuery(sql);

            sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE rentalFeaturesId = " + rentalFeaturesId;

            ResultSet rs2 = stmt.executeQuery(sql);

            if (!rs1.first() | !rs2.first()) return null;

            List<DateRange> dateRanges = new ArrayList<>();

            do {
                dateRanges.add(new DateRange(rs2.getDate("startDate").toLocalDate(), rs2.getDate("endDate").toLocalDate()));
            } while (rs2.next());


            rentalFeatures = new RentalFeatures(rentalFeaturesId, rs1.getString("description"), rs1.getInt("price"), rs1.getInt("deposit"), dateRanges);


            rs1.close();
            rs2.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rentalFeatures;

    }


}
