package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.List;

public interface ContractRequestDAO {
    public void insertNewRequest(ContractRequestBean contractRequestBean) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException;
    public List<ContractRequestId> findContractRequestIdsByRenterNickname(String renterNickname) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
    public ContractRequest getContractRequest(ContractRequestId contractRequestId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
}
