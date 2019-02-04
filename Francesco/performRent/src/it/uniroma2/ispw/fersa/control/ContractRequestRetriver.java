package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.PeriodException;

import java.sql.SQLException;

public abstract class ContractRequestRetriver {

    public abstract ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, PeriodException;


}
