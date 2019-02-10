package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.Contract;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractId;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequest;
import it.uniroma2.ispw.fersa.rentingManagement.entity.ContractRequestId;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TenantRequestHandlerSession {
    private String tenantNickname;
    private ContractRequest contractRequest;

    public TenantRequestHandlerSession(String tenantNickname) {
        this.tenantNickname = tenantNickname;
    }

    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestJDBC.getInstance().findContractRequestIdsByTenantNickname(this.tenantNickname);
        for (ContractRequestId requestId : contractRequestIds) {
            try {
                ContractsAndRequestRetriver contractsAndRequestRetriver =
                        new ContractTypeDecorator(new ServiceDecorator(new ContractsAndRequestSimpleRetriver(requestId)));
                ContractRequest contractRequest = contractsAndRequestRetriver.retriveContractRequest();
                requestLabelBeans.add(new RequestLabelBean(contractRequest.getRequestId().getId(),
                        contractRequest.getTenantNickname(), contractRequest.getCreationDate(),
                        contractRequest.getStartDate(), contractRequest.getEndDate(), contractRequest.getTotal(),
                        contractRequest.getState()));
            } catch (ContractPeriodException e) {
                e.printStackTrace();
            }
        }
        return requestLabelBeans;
    }

    public void selectRequest(ContractRequestId requestId) throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, ContractPeriodException {
        ContractsAndRequestRetriver contractsAndRequestRetriver =
                new ContractTypeDecorator(new ServiceDecorator(new ContractsAndRequestSimpleRetriver(requestId)));
        this.contractRequest = contractsAndRequestRetriver.retriveContractRequest();
    }








}
