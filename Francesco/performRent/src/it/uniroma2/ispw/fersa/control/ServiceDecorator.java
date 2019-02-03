package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ServiceJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.Service;

import java.util.List;

public class ServiceDecorator extends ContractRequestRetriverDecorator {
    public ServiceDecorator(ContractRequestRetriver contractRequestRetriver) {
        super(contractRequestRetriver);
    }

    /**public ContractRequest setServices(ContractRequest contractRequest) {
        ContractRequestId requestId = contractRequest.getRequestId();
        List<Service> services = ServiceJDBC.getInstance().getServiceByRequestId(requestId);
        contractRequest.setServices(services);
        return contractRequest;
    }**/

    @Override
    public ContractRequest retriveContractRequest() {
        ContractRequest preliminaryResult = super.retriveContractRequest();
        //preliminaryResult = setServices(preliminaryResult);
        return preliminaryResult;
    }
}
