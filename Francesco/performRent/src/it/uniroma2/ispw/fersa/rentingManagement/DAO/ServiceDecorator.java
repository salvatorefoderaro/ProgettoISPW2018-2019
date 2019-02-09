package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.List;

public class ServiceDecorator extends ContractsAndRequestRetriverDecorator {
    public ServiceDecorator(ContractsAndRequestRetriver contractsAndRequestRetriver) {
        super(contractsAndRequestRetriver);
    }

    public ContractRequest setServices(ContractRequest contractRequest) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        ContractRequestId requestId = contractRequest.getRequestId();
        List<Service> services = ServiceJDBC.getInstance().getServiceByContractRequestId(requestId);
        contractRequest.setServices(services);
        return contractRequest;
    }

    public Contract setServices(Contract contract) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        ContractId contractId = contract.getContractId();
        List<Service> services = ServiceJDBC.getInstance().getServiceByContractId(contractId);
        contract.setServices(services);
        return contract;
    }

    @Override
    public ContractRequest retriveContractRequest() throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, ContractPeriodException {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract preliminaryResult = super.retriveContract();
        setServices(preliminaryResult);
        return preliminaryResult;
    }

}
