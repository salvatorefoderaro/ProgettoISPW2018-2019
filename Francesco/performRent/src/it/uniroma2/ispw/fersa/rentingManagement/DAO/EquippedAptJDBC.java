package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.*;

public class EquippedAptJDBC implements EquippedAptDAO{

    private static EquippedAptJDBC equippedAptJDBC;

    protected EquippedAptJDBC(){

    }

    public static synchronized EquippedAptJDBC getInstance(){
        if (equippedAptJDBC == null) {
            equippedAptJDBC = new EquippedAptJDBC();
        }
        return equippedAptJDBC;
    }

    @Override
    public EquippedApt getEquippedApt(int aptId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException {
        EquippedApt equippedApt = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT id, renterNickname, address FROM AptToRent WHERE id = " + aptId;

            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first()) return null;

            equippedApt = new EquippedApt(rs.getInt("id"), rs.getString("renterNickname"), rs.getString("address"));

            rs.close();
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

        return equippedApt;
    }





}
