package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import it.uniroma2.ispw.fersa.userProfileAndServices.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RenterRequestHandlerSession {
    private String renterNickname;
    private ContractRequest contractRequest;
    private Contract contract;

    public RenterRequestHandlerSession(String renterNickname) {
        this.renterNickname = renterNickname;
    }

    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.renterNickname);
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

    public ContractTextBean getContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException{
        EquippedApt equippedApt = EquippedAptJDBC.getInstance().getEquippedAptByContractRequestId(this.contractRequest.getContractRequestId());

        List<ServiceBean> serviceBeans = new ArrayList<>();

        this.contract.getServices().forEach(service -> serviceBeans.add(new ServiceBean(service.getId(),
                service.getName(), service.getDescriprion(), service.getPrice())));

        return new ContractTextBean(this.contract.getContractTypeName(), this.contract.isTransitory(),
                equippedApt.getAddress(), this.contract.getStartDate(), this.contract.getEndDate(),
                this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(),
                this.contract.getTenantDateOfBirth(), this.contract.getTenantCityOfBirth(),
                this.contract.getTenantAddress(), this.contract.getRenterName(),
                this.contract.getRenterSurname(), this.contract.getRenterCF(), this.contract.getRenterAddress(),
                this.contract.getNumMonths(), this.contract.getNetPrice(), this.contract.getDeposit(), serviceBeans);
    }

    public PropertyBean getPropertyInfo()  throws SQLException, ClassNotFoundException, ConfigException,
            ConfigFileException, IOException {
        EquippedApt apt = EquippedAptJDBC.getInstance().getEquippedAptByContractRequestId(this.contractRequest.getContractRequestId());

        Property property = RentableJDBC.getInstance().getRentableByContractRequestId(
                this.contractRequest.getContractRequestId());
        return new PropertyBean(apt.getAddress(), property.getName(), property.getImage(), property.getType(),
                property.getDescription());
    }

    public void createContract() throws NicknameNotFoundException, SQLException, ClassNotFoundException, ConfigException, ConfigFileException{
        UserProfileInterface userProfile = new UserLoaderFAKE();
        UserInfo tenantInfo, renterInfo;
        ContractType contractType;

        tenantInfo = userProfile.getUserInfo(this.contractRequest.getTenantNickname(), UserInfoType.TENANT);
        renterInfo = userProfile.getUserInfo(this.renterNickname, UserInfoType.RENTER);
        contractType = ContractTypeJDBC.getIstance().getContractTypeById(this.contractRequest.getContractTypeId());



        this.contract = new Contract(this.contractRequest.getRentableId(), this.renterNickname,tenantInfo.getNickname(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(), tenantInfo.getName(),
                tenantInfo.getSurname(), tenantInfo.getCF(), tenantInfo.getDateOfBirth(), tenantInfo.getCityOfBirth(),
                tenantInfo.getAddress(), renterInfo.getName(), renterInfo.getSurname(), renterInfo.getCF(),
                renterInfo.getAddress(), this.contractRequest.getRentablePrice(), this.contractRequest.getDeposit(),
                contractType, this.contractRequest.getServices());
    }

    public void declineRequest(String declineMotivation) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequestJDBC.getInstance().refuseRequest(this.contractRequest.getContractRequestId(), declineMotivation);
    }

    public boolean isRequestSelected() {
        return this.contractRequest != null;
    }

    public void signContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractBean contractBean = new ContractBean(this.contractRequest.getContractRequestId(), this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(), this.contract.getTenantDateOfBirth(), this.contract.getTenantCityOfBirth(), this.contract.getTenantAddress(), this.contract.getRenterName(), this.contract.getRenterSurname(), this.contract.getRenterCF(), this.contract.getRenterAddress(), this.contract.getGrossPrice(), this.contract.getNetPrice(), 1);
        ContractJDBC.getInstance().createContract(contractBean);
    }
}
