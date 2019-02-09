package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public abstract class ContractsAndRequestRetriverDecorator extends ContractsAndRequestRetriver {
    private ContractsAndRequestRetriver contractsAndRequestRetriver;

    public ContractsAndRequestRetriverDecorator(ContractsAndRequestRetriver contractsAndRequestRetriver) {
        this.contractsAndRequestRetriver = contractsAndRequestRetriver;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest contractRequest = this.contractsAndRequestRetriver.retriveContractRequest();
        return contractRequest;
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract contract = this.contractsAndRequestRetriver.retriveContract();
        return contract;
    }
}
