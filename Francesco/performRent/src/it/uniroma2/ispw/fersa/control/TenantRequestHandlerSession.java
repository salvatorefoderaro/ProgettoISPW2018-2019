package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.CanceledRequestException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TenantRequestHandlerSession extends RequestHandlerSession{
    private String tenantNickname;

    public TenantRequestHandlerSession(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestDAO.getInstance().findContractRequestIdsByTenantNickname(this.tenantNickname);
        for (ContractRequestId requestId : contractRequestIds) {
            try {
                ContractsAndRequestLoader contractsAndRequestLoader =
                        new ContractTypeDecorator(new ServiceDecorator(new ContractsAndRequestSimpleLoader(requestId)));
                ContractRequest contractRequest = contractsAndRequestLoader.retriveContractRequest();
                requestLabelBeans.add(new RequestLabelBean(contractRequest.getRequestId().getId(),
                        contractRequest.getRenterNickname(), contractRequest.getCreationDate(),
                        contractRequest.getStartDate(), contractRequest.getEndDate(), contractRequest.getTotal(),
                        contractRequest.getState()));
            } catch (ContractPeriodException e) {
                e.printStackTrace();
            }
        }
        return requestLabelBeans;
    }

    public void cancelRequest() throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException, CanceledRequestException {
        ContractRequestDAO.getInstance().cancelRequest(this.contractRequest.getContractRequestId());

    }
}
