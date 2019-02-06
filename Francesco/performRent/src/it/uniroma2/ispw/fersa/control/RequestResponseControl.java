package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.ContractRequestJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.DAO.EquippedAptJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.DAO.RentableJDBC;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ContractRequestInfoBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.PropertyBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.RequestLabelBean;
import it.uniroma2.ispw.fersa.rentingManagement.bean.ServiceBean;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;

public class RequestResponseControl {
    private String renterNickname;

    private ContractRequest contractRequest;

    public RequestResponseControl(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.renterNickname);
        for (ContractRequestId requestId : contractRequestIds) {
            try {
                ContractRequestRetriver contractRequestRetriver =
                        new ContractTypeDecorator(new ServiceDecorator(new ContractRequestSimpleRetriver(requestId)));
                ContractRequest contractRequest = contractRequestRetriver.retriveContractRequest();
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
            ConfigFileException, ContractPeriodException{
        ContractRequestRetriver contractRequestRetriver =
                new ContractTypeDecorator(new ServiceDecorator(new ContractRequestSimpleRetriver(requestId)));
        this.contractRequest = contractRequestRetriver.retriveContractRequest();
    }

    public PropertyBean getPropertyInfo()  throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, IOException {
        EquippedApt apt = EquippedAptJDBC.getInstance().getEquippedAptByContractRequestId(this.contractRequest.getContractRequestId());

        Rentable rentable = RentableJDBC.getInstance().getRentableByContractRequestId(
                this.contractRequest.getContractRequestId());
        System.out.println(apt);
        System.out.println(rentable);
        return new PropertyBean(apt.getAddress(), rentable.getName(), rentable.getImage(), rentable.getType(),
                rentable.getDescription());
    }

    public ContractRequestInfoBean getRequestInfo() {
        List<Service> services = this.contractRequest.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        System.out.println("Valore" + this.contractRequest.getState());

        return new ContractRequestInfoBean(this.contractRequest.getContractName(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(),
                this.contractRequest.getRentablePrice(),
                this.contractRequest.getDeposit(),serviceBeans, this.contractRequest.getTotal(), this.contractRequest.getState(), this.contractRequest.getDeclineMotivation());
    }

    public void declineRequest(String declineMotivation) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequestJDBC.getInstance().refuseRequest(this.contractRequest.getContractRequestId(), declineMotivation);
    }
}
