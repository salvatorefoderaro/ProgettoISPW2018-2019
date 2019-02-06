package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public interface EquippedAptDAO {
    public EquippedApt getEquippedAptByContractRequestId(ContractRequestId contractRequestId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException;
    public EquippedApt getEquippedAptById(int aptId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException;
}
