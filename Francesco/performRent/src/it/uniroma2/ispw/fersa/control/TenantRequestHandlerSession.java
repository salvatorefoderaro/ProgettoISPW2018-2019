package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.io.IOException;
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
                        contractRequest.getRenterNickname(), contractRequest.getCreationDate(),
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

    public PropertyBean getPropertyInfo()  throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, IOException {
        EquippedApt apt = EquippedAptJDBC.getInstance().getEquippedAptByContractRequestId(this.contractRequest.getContractRequestId());

        Rentable rentable = RentableJDBC.getInstance().getRentableByContractRequestId(
                this.contractRequest.getContractRequestId());
        return new PropertyBean(apt.getAddress(), rentable.getName(), rentable.getImage(), rentable.getType(),
                rentable.getDescription());
    }

    public ContractRequestInfoBean getRequestInfo() {
        List<Service> services = this.contractRequest.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractRequestInfoBean(this.contractRequest.getContractName(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(),
                this.contractRequest.getRentablePrice(),
                this.contractRequest.getDeposit(),serviceBeans, this.contractRequest.getTotal(), this.contractRequest.getState(), this.contractRequest.getDeclineMotivation());
    }

    public void cancelRequest() throws SQLException, ClassNotFoundException, ConfigException, ConfigFileException{
        ContractRequestJDBC.getInstance().cancelRequest(this.contractRequest.getContractRequestId());

    }
}
