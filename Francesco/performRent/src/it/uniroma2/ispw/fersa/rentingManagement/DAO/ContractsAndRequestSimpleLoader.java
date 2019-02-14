package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public class ContractsAndRequestSimpleLoader extends ContractsAndRequestLoader {

    private ContractRequestId contractRequestId;
    private ContractId contractId;

    public ContractsAndRequestSimpleLoader(ContractRequestId contractRequestId) {
        this.contractRequestId = contractRequestId;
    }

    public ContractsAndRequestSimpleLoader(ContractId contractId) {
        this.contractId = contractId;
    }


    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractRequestDAO.getInstance().getContractRequest(this.contractRequestId);
    }

    @Override
    public Contract retriveContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractDAO.getInstance().getContractById(this.contractId);
    }

}

