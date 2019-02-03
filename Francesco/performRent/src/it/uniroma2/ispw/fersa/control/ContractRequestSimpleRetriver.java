package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ContractRequestJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;

public class ContractRequestSimpleRetriver extends ContractRequestRetriver {

    private ContractRequestId ContractRequestId;

    public ContractRequestSimpleRetriver(ContractRequestId contractRequestId) {
        this.ContractRequestId = contractRequestId;
    }

    @Override
    public ContractRequest retriveContractRequest(){
        return ContractRequestJDBC.getInstance().getContractRequest(this.ContractRequestId);

    }

}

