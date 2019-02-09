package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;

public class ContractsAndRequestSimpleRetriver extends ContractsAndRequestRetriver {

    private ContractRequestId contractRequestId;
    private ContractId contractId;

    public ContractsAndRequestSimpleRetriver(ContractRequestId contractRequestId) {
        this.contractRequestId = contractRequestId;
    }

    public ContractsAndRequestSimpleRetriver(ContractId contractId) {
        this.contractId = contractId;
    }


    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractRequestJDBC.getInstance().getContractRequest(this.contractRequestId);
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractJDBC.getInstance().getContractById(this.contractId);
    }

}

