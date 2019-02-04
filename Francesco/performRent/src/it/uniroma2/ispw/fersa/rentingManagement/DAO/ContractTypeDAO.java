package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;
import java.util.List;

public interface ContractTypeDAO {
    public List<ContractType> getAllContractTypes() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException;
    public ContractType getContractTypeById(int contractTypeId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException;
    public ContractType getContractTypeByRequestId(ContractRequestId requestId) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException;
    public ContractType getContractTypeByName(String contractTypeName) throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException;
}
