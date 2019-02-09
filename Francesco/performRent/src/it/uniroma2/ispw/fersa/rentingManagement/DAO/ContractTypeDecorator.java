package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public class ContractTypeDecorator extends ContractsAndRequestRetriverDecorator {
    public ContractTypeDecorator(ContractsAndRequestRetriver contractsAndRequestRetriver) {
        super(contractsAndRequestRetriver);
    }

    public ContractRequest setContractType(ContractRequest contractRequest) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequestId contractRequestId = contractRequest.getRequestId();
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByRequestId(contractRequestId);
        contractRequest.setContractType(contractType);
        return contractRequest;
    }
    public Contract setContractType(Contract contract) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractId contractId = contract.getContractId();
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByContractId(contractId);
        contract.setContractType(contractType);
        return contract;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract preliminaryResult = super.retriveContract();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }
}
