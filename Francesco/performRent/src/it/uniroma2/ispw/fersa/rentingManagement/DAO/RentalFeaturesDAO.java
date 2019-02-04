package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.RentalFeatures;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public interface RentalFeaturesDAO {
    public RentalFeatures getRentalFeatures(int rentalFeaturesId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
}
