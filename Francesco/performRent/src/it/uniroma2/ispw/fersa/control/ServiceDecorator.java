package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ServiceJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.PeriodException;

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
    public ContractRequest retriveContractRequest() throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, PeriodException {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }
}
