package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ContractRequestJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;

import java.sql.SQLException;

public class ContractRequestSimpleRetriver extends ContractRequestRetriver {

    private ContractRequestId ContractRequestId;

    public ContractRequestSimpleRetriver(ContractRequestId contractRequestId) {
        this.ContractRequestId = contractRequestId;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractRequestJDBC.getInstance().getContractRequest(this.ContractRequestId);


    }

}

