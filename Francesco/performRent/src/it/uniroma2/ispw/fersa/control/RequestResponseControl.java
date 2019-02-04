package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.DAO.ContractRequestJDBC;

import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.performContractRequest.entity.ContractRequestId;

import java.util.ArrayList;
import java.util.List;

public class RequestResponseControl {
    private String renterNickname;

    private ContractRequest contractRequest;

    public RequestResponseControl(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    /*public List<RequestLabelBean> getAllContractRequest() {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds = ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.renterNickname);
        contractRequestIds.forEach(contractRequestId -> {
            ContractRequestRetriver contractRequestRetriver = new ContractTypeDecorator(new ServiceDecorator( new ContractRequestSimpleRetriver(contractRequestId)));
            ContractRequest contractRequest = contractRequestRetriver.retriveContractRequest();
            requestLabelBeans.add(new RequestLabelBean(contractRequest.getRequestId().getId(), contractRequest.getTenantNickname(), contractRequest.getCreationDate(), contractRequest.getStartDate(), contractRequest.getEndDate(), contractRequest.getTotal(), contractRequest.getState()));
        });
        return requestLabelBeans;
    }*/





}
