package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Rentable;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.io.IOException;
import java.sql.SQLException;

public interface RentableDAO {
    public Rentable getRentable(int rentalFeaturesId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, IOException;
}
