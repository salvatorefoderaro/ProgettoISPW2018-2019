package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.EquippedApt;

import java.sql.*;

public class EquippedAptJDBC {

    private static EquippedAptJDBC equippedAptJDBC;

    private static String USER = "root";

    private static String PASS = "Francesco1997";

    private static String DB_URL = "jdbc:mariadb://localhost:3306/RentingManagement";

    private static String DRIVER_CLASS_NAME = "org.mariadb.jdbc.Driver";

    protected EquippedAptJDBC(){

    }

    public static synchronized EquippedAptJDBC getInstance(){
        if (equippedAptJDBC == null) {
            equippedAptJDBC = new EquippedAptJDBC();
        }
        return equippedAptJDBC;
    }

    public EquippedApt getEquippedApt(int aptId) {
        EquippedApt equippedApt = null;

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(DRIVER_CLASS_NAME);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, renterNickname, address FROM AptToRent WHERE id = " + aptId;

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            equippedApt = new EquippedApt(rs.getInt("id"), rs.getString("renterNickname"), rs.getString("address"));

            rs.close();
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

        return equippedApt;
    }





}
