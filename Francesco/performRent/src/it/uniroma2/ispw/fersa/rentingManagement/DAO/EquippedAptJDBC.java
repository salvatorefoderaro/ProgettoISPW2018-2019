package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.entity.RentableTypeEnum;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import javax.imageio.ImageIO;
import java.io.IOException;
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
    public EquippedApt getEquippedAptByContractRequestId(ContractRequestId contractRequestId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException{
        return this.getEquippedApt("Select AptToRent.id, AptToRent.renterNickname, address FROM AptToRent INNER JOIN ContractRequest ON AptToRent.id = ContractRequest.aptId WHERE ContractRequest.id = " + contractRequestId.getId());
    }


    @Override
    public EquippedApt getEquippedAptById(int aptId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException {
        return getEquippedApt("SELECT id, renterNickname, address FROM AptToRent WHERE id = " + aptId);
    }

    private EquippedApt getEquippedApt(String sql) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException {
        EquippedApt equippedApt = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


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
