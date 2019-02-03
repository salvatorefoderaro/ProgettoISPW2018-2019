package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ContractTypeJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractType;

public class ContractTypeDecorator extends ContractRequestRetriverDecorator{
    public ContractTypeDecorator(ContractRequestRetriver contractRequestRetriver) {
        super(contractRequestRetriver);
    }

    public ContractRequest setContractType(ContractRequest contractRequest) {
        ContractRequestId contractRequestId = contractRequest.getRequestId();
        ContractType contractType = ContractTypeJDBC.getIstance().getContractTypeByRequestId(contractRequestId);
        contractRequest.setContractType(contractType);
        return contractRequest;
    }

    @Override
    public ContractRequest retriveContractRequest() {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        preliminaryResult = setContractType(preliminaryResult);
        return preliminaryResult;
    }
}
