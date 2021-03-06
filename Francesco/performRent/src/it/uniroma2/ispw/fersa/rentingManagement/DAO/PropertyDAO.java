package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Property;
import it.uniroma2.ispw.fersa.rentingManagement.entity.PropertyTypeEnum;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.*;

public class PropertyDAO {
    private static PropertyDAO ourInstance = new PropertyDAO();

    public static PropertyDAO getInstance() {
        return ourInstance;
    }

    private PropertyDAO() {
    }

    public Property getPropertyByRentalFeaturesId(int rentalFeaturesId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException {
      return this.getProperty("SELECT aptToRentId, roomToRentId, bedToRentId, type FROM RentalFeatures WHERE id = " + rentalFeaturesId);
    }

    public Property getPropertyByContractRequestId(ContractRequestId contractRequestId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException {
        return this.getProperty("SELECT aptToRentId, roomToRentId, bedToRentId, type FROM ContractRequest WHERE id = " + contractRequestId.getId());
    }


    public Property getPropertyByContractId(ContractId contractId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException {
        return this.getProperty("SELECT aptToRentId, roomToRentId, bedToRentId, type FROM Contract WHERE contractId = " + contractId.getContractId());
    }


    private Property getProperty(String sql) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException {
        Property property = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;


        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


            ResultSet rs1 = stmt.executeQuery(sql);

            String tabel = null;
            String column = null;

            if (!rs1.first()) return null;

            switch(PropertyTypeEnum.valueOf(rs1.getString("type"))) {
                case APTTORENT:
                    tabel = "AptToRent";
                    column = "aptToRentId";
                    break;
                case ROOMTORENT:
                    tabel = "RoomToRent";
                    column = "roomToRentId";
                    break;
                case BEDTORENT:
                    tabel = "BedToRent";
                    column = "bedToRentId";
                    break;
            }


            sql = "SELECT id, name, description, image FROM " + tabel + " WHERE id = " + rs1.getString(column);

            ResultSet rs2 = stmt.executeQuery(sql);

            if (!rs2.first()) return null;


            property = new Property(rs1.getInt(column), PropertyTypeEnum.valueOf(rs1.getString("type")),rs2.getString("name"), rs2.getString("description"), ImageIO.read(rs2.getBinaryStream("image")));


            rs1.close();
            rs2.close();
            stmt.close();
            conn.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return property;
    }


}
