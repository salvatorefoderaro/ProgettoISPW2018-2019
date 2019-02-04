package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.RentalFeatures;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.sql.SQLException;

public interface RentalFeaturesDAO {
    public RentalFeatures getRentalFeatures(int rentalFeaturesId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
}
