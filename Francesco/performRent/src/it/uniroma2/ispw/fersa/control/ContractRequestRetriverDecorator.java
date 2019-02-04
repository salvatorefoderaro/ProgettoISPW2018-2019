package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.exception.PeriodException;

import java.sql.SQLException;

public abstract class ContractRequestRetriverDecorator extends ContractRequestRetriver{
    private ContractRequestRetriver contractRequestRetriver;

    public ContractRequestRetriverDecorator(ContractRequestRetriver contractRequestRetriver) {
        this.contractRequestRetriver = contractRequestRetriver;
    }

    @Override
    public ContractRequest retriveContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, PeriodException {
        ContractRequest contractRequest = this.contractRequestRetriver.retriveContractRequest();
        return contractRequest;
    }
}
