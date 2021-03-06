package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public abstract class ContractLoader {

    public abstract Contract loadContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;

}
