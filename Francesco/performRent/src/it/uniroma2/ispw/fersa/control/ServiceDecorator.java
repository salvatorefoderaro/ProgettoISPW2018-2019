package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ServiceJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.List;

public class ServiceDecorator extends ContractRequestRetriverDecorator {
    public ServiceDecorator(ContractRequestRetriver contractRequestRetriver) {
        super(contractRequestRetriver);
    }

    public ContractRequest setServices(ContractRequest contractRequest) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        ContractRequestId requestId = contractRequest.getRequestId();
        List<Service> services = ServiceJDBC.getInstance().getServiceByRequestId(requestId);
        contractRequest.setServices(services);
        return contractRequest;
    }

    @Override
    public ContractRequest retriveContractRequest() throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, ContractPeriodException {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }
}
