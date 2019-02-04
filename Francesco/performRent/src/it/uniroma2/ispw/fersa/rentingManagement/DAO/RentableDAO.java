package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.io.IOException;
import java.sql.SQLException;

public interface RentableDAO {
    public Rentable getRentable(int rentalFeaturesId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException;
}
