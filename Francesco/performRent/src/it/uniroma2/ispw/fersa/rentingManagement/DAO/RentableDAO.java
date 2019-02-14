package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Property;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.io.IOException;
import java.sql.SQLException;

public interface RentableDAO {
    public Property getRentableByRentalFeaturesId(int rentalFeaturesId) throws ConfigFileException, ConfigException,
            ClassNotFoundException, SQLException, IOException;
    public Property getRentableByContractRequestId(ContractRequestId contractRequestId) throws ConfigFileException,
            ConfigException, ClassNotFoundException, SQLException, IOException;
    }
