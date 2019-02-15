package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public class RequestContractTypeDecorator extends RequestLoaderDecorator {
    public RequestContractTypeDecorator(RequestLoader requestLoader) {
        super(requestLoader);
    }

    public ContractRequest setContractType(ContractRequest contractRequest) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequestId contractRequestId = contractRequest.getRequestId();
        ContractType contractType = ContractTypeDAO.getIstance().getContractTypeByRequestId(contractRequestId);
        contractRequest.setContractType(contractType);
        return contractRequest;
    }


    @Override
    public ContractRequest loadContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest preliminaryResult = super.loadContractRequest();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }

}
