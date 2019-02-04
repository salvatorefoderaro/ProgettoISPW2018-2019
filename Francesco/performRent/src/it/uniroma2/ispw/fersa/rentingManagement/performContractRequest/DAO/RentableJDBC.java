package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RentableTypeEnum;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.*;

public class RentableJDBC implements RentableDAO{
    private static RentableJDBC ourInstance = new RentableJDBC();

    public static RentableJDBC getInstance() {
        return ourInstance;
    }

    private RentableJDBC() {
    }

    @Override
    public Rentable getRentable(int rentalFeaturesId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException {
        Rentable rentable = null;

        Connection conn = ConnectionFactory.getInstance().openConnection();
        Statement stmt = null;


        try {

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


            rentable = new Rentable(rs1.getInt(column), RentableTypeEnum.valueOf(rs1.getString("type")),rs2.getString("name"), rs2.getString("description"), ImageIO.read(rs2.getBinaryStream("image")));


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
        return rentable;
    }
}
