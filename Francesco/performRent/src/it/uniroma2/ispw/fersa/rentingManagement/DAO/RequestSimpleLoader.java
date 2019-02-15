package it.uniroma2.ispw.fersa.rentingManagement.DAO;

import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;

import java.sql.SQLException;

public class RequestSimpleLoader extends RequestLoader {

    private ContractRequestId contractRequestId;


    public RequestSimpleLoader(ContractRequestId contractRequestId) {
        this.contractRequestId = contractRequestId;
    }


    @Override
    public ContractRequest loadContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        return ContractRequestDAO.getInstance().getContractRequest(this.contractRequestId);
    }


}

