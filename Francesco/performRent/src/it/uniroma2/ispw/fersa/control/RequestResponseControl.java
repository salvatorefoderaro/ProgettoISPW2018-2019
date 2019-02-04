package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractRequestJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.PeriodException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestResponseControl {
    private String renterNickname;

    private ContractRequest contractRequest;

    public RequestResponseControl(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds = ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.renterNickname);
        for (ContractRequestId requestId : contractRequestIds) {
            try {
                ContractRequestRetriver contractRequestRetriver = new ContractTypeDecorator(new ServiceDecorator(new ContractRequestSimpleRetriver(requestId)));
                ContractRequest contractRequest = contractRequestRetriver.retriveContractRequest();
                requestLabelBeans.add(new RequestLabelBean(contractRequest.getRequestId().getId(), contractRequest.getTenantNickname(), contractRequest.getCreationDate(), contractRequest.getStartDate(), contractRequest.getEndDate(), contractRequest.getTotal(), contractRequest.getState()));
            } catch (ContractPeriodException e) {
                e.printStackTrace();
            }
        }

        return requestLabelBeans;
    }





}
