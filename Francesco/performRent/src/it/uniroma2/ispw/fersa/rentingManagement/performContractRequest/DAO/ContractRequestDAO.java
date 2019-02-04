package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.ContractRequestBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.PeriodException;

import java.sql.SQLException;
import java.util.List;

public interface ContractRequestDAO {
    public void insertNewRequest(ContractRequestBean contractRequestBean) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, PeriodException;
    public List<ContractRequestId> findContractRequestIdsByRenterNickname(String renterNickname) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
    public ContractRequest getContractRequest(ContractRequestId contractRequestId) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
}
