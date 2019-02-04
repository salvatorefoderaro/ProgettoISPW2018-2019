package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractRequestJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

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

