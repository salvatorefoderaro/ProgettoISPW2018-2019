package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.sql.SQLException;

public interface EquippedAptDAO {
    public EquippedApt getEquippedApt(int aptId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException;


}
