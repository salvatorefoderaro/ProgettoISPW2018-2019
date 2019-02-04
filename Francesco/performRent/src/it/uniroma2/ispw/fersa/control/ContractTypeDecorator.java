package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractTypeJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractType;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public class ContractTypeDecorator extends ContractRequestRetriverDecorator{
    public ContractTypeDecorator(ContractRequestRetriver contractRequestRetriver) {
        super(contractRequestRetriver);
    }

    public ContractRequest setContractType(ContractRequest contractRequest) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequestId contractRequestId = contractRequest.getRequestId();
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByRequestId(contractRequestId);
        contractRequest.setContractType(contractType);
        return contractRequest;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }
}
