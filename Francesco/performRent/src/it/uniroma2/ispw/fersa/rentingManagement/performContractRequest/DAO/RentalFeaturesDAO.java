package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.IntervalDate;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RentalFeatures;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalFeaturesDAO {

    protected static RentalFeaturesDAO rentalFeaturesDAO;

    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";


    protected RentalFeaturesDAO(){

    }

    public static synchronized RentalFeaturesDAO getInstance() {
        if (rentalFeaturesDAO == null) {
            rentalFeaturesDAO = new RentalFeaturesDAO();
        }
        return rentalFeaturesDAO;
    }

    //TODO Modificare quando si introduce il db
    public RentalFeatures getRentalFeatures(int rentalFeaturesId) {

        Connection conn = null;
        Statement stmt = null;
        RentalFeatures rentalFeatures = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, description, price, deposit FROM RentalFeatures WHERE id = " + rentalFeaturesId;

            ResultSet rs1 = stmt.executeQuery(sql);

            sql = "SELECT startDate, endDate FROM AvailabilityCalendar WHERE renterFeaturesId = " + rentalFeaturesId;

            ResultSet rs2 = stmt.executeQuery(sql);

            if (!rs1.first()) return null;

            List<IntervalDate> intervalDates = new ArrayList<IntervalDate>();

            if (rs2.first()) {
                do {
                    intervalDates.add(new IntervalDate(rs2.getDate("startDate").toLocalDate(), rs2.getDate("endDate").toLocalDate()));
                } while (rs2.next());
            }

            rentalFeatures = new RentalFeatures(rentalFeaturesId, rs1.getString("description"), rs1.getInt("price"), rs1.getInt("deposit"), intervalDates);


            rs1.close();
            rs2.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        }

        return rentalFeatures;

    }


}
