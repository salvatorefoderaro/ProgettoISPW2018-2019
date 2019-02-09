package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;
import java.util.List;

public interface ServiceDAO {

    public Service getServiceByContractRequestId(int serviceId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;

    public List<Service> getServicesByAptId(int aptId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;

    public List<Service> getServiceByContractRequestId(ContractRequestId requestId) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException;
}
