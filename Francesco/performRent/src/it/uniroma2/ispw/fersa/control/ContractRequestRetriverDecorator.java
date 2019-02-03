package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;

public abstract class ContractRequestRetriverDecorator extends ContractRequestRetriver{
    private ContractRequestRetriver contractRequestRetriver;

    public ContractRequestRetriverDecorator(ContractRequestRetriver contractRequestRetriver) {
        this.contractRequestRetriver = contractRequestRetriver;
    }

    @Override
    public ContractRequest retriveContractRequest() {
        ContractRequest contractRequest = this.contractRequestRetriver.retriveContractRequest();
        return contractRequest;
    }
}
