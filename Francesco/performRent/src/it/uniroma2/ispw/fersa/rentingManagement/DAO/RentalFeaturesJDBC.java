package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.IntervalDate;
import it.uniroma2.ispw.fersa.rentingManagement.entity.RentalFeatures;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalFeaturesJDBC implements RentalFeaturesDAO {

    protected static RentalFeaturesJDBC rentalFeaturesJDBC;


    protected RentalFeaturesJDBC(){

    }

    public static synchronized RentalFeaturesJDBC getInstance() {
        if (rentalFeaturesJDBC == null) {
            rentalFeaturesJDBC = new RentalFeaturesJDBC();
        }
        return rentalFeaturesJDBC;
    }

    @Override
    public RentalFeatures getRentalFeatures(int rentalFeaturesId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;
        RentalFeatures rentalFeatures = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, description, price, deposit FROM RentalFeatures WHERE id = " + rentalFeaturesId;

            ResultSet rs1 = stmt.executeQuery(sql);

            sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE renterFeaturesId = " + rentalFeaturesId;

            ResultSet rs2 = stmt.executeQuery(sql);

            if (!rs1.first() | !rs2.first()) return null;

            List<IntervalDate> intervalDates = new ArrayList<>();

            do {
                intervalDates.add(new IntervalDate(rs2.getDate("startDate").toLocalDate(), rs2.getDate("endDate").toLocalDate()));
            } while (rs2.next());


            rentalFeatures = new RentalFeatures(rentalFeaturesId, rs1.getString("description"), rs1.getInt("price"), rs1.getInt("deposit"), intervalDates);


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
