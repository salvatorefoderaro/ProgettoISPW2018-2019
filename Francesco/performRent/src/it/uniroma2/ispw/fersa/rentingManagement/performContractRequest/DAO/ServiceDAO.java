package it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.sql.SQLException;
import java.util.List;

public interface ServiceDAO {

    public Service getServiceById(int serviceId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;

    public List<Service> getServicesByAptId(int aptId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;

    public List<Service> getServiceByRequestId(ContractRequestId requestId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;
}
