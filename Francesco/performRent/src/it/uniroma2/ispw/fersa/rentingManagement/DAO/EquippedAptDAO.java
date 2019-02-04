package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.EquippedApt;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public interface EquippedAptDAO {
    public EquippedApt getEquippedApt(int aptId) throws ClassNotFoundException, SQLException, ConfigException, ConfigFileException;


}
