package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.List;

public class RequestServiceDecorator extends RequestLoaderDecorator {
    public RequestServiceDecorator(RequestLoader requestLoader) {
        super(requestLoader);
    }

    public ContractRequest setServices(ContractRequest contractRequest) throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException {
        ContractRequestId requestId = contractRequest.getRequestId();
        List<Service> services = ServiceDAO.getInstance().getServicesByContractRequestId(requestId);
        contractRequest.setServices(services);
        return contractRequest;
    }


    @Override
    public ContractRequest loadContractRequest() throws ConfigFileException, ConfigException, ClassNotFoundException, SQLException, ContractPeriodException {
        ContractRequest preliminaryResult = super.loadContractRequest();
        preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }


}
