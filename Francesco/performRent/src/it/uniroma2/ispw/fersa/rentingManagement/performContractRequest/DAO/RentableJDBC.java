package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RentableTypeEnum;

import javax.imageio.ImageIO;
import java.sql.*;

public class RentableJDBC {
    private static RentableJDBC ourInstance = new RentableJDBC();

    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    public static RentableJDBC getInstance() {
        return ourInstance;
    }

    private RentableJDBC() {
    }

    public Rentable getRentable(int rentalFeaturesId) {

        Connection conn = null;
        Statement stmt = null;
        Rentable rentable = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT aptToRentId, roomToRentId, bedToRentId, type FROM RentalFeatures WHERE id = " + rentalFeaturesId;

            ResultSet rs1 = stmt.executeQuery(sql);

            String tabel = null;
            String column = null;

            if (!rs1.first()) return null;

            switch(RentableTypeEnum.valueOf(rs1.getString("type"))) {
                case APTTORENT:
                    tabel = "AptToRent";
                    column = "aptToRentId";
                    break;
                case ROOMTORENT:
                    tabel = "RoomToRent";
                    column = "roomToRentId";
                    break;
                case BEDTORENT:
                    tabel = "BedToTent";
                    column = "bedToRentId";
                    break;
            }


            sql = "SELECT id, name, description, image FROM " + tabel + " WHERE id = " + rs1.getString(column);

            ResultSet rs2 = stmt.executeQuery(sql);

            if (!rs2.first()) return null;


            rentable = new Rentable(rs2.getString("name"), rs2.getString("description"), ImageIO.read(rs2.getBinaryStream("image")));


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
        return rentable;
    }
}
