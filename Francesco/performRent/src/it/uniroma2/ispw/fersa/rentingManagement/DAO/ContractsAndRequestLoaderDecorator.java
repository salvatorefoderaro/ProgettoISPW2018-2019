package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public abstract class ContractsAndRequestLoaderDecorator extends ContractsAndRequestLoader {
    private ContractsAndRequestLoader contractsAndRequestLoader;

    public ContractsAndRequestLoaderDecorator(ContractsAndRequestLoader contractsAndRequestLoader) {
        this.contractsAndRequestLoader = contractsAndRequestLoader;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractRequest contractRequest = this.contractsAndRequestLoader.retriveContractRequest();
        return contractRequest;
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        Contract contract = this.contractsAndRequestLoader.retriveContract();
        return contract;
    }
}
