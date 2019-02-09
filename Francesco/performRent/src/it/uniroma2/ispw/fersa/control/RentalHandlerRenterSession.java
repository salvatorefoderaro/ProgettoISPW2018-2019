package it.uniroma2.ispw.fersa.control;

import it.uniroma2.ispw.fersa.rentingManagement.DAO.*;
import it.uniroma2.ispw.fersa.rentingManagement.bean.*;
import it.uniroma2.ispw.fersa.rentingManagement.entity.*;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ConfigFileException;
import it.uniroma2.ispw.fersa.rentingManagement.exception.ContractPeriodException;
import it.uniroma2.ispw.fersa.userProfileAndServices.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalHandlerRenterSession extends RentalHandlerSession{


    public RentalHandlerRenterSession(String renterNickname) {
        super(renterNickname);
    }

    @Override
    public List<RequestLabelBean> getAllContractRequest() throws SQLException, ClassNotFoundException,
            ConfigFileException, ConfigException {
        List<RequestLabelBean> requestLabelBeans = new ArrayList<>();

        List<ContractRequestId> contractRequestIds =
                ContractRequestJDBC.getInstance().findContractRequestIdsByRenterNickname(this.nickname);
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


    public void declineRequest(String declineMotivation) throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        ContractRequestJDBC.getInstance().refuseRequest(this.contractRequest.getContractRequestId(), declineMotivation);
    }

    public void createContract() throws NicknameNotFoundException, SQLException, ClassNotFoundException, ConfigException, ConfigFileException{
        UserProfileInterface userProfile = new UserLoaderFAKE();
        UserInfo tenantInfo, renterInfo;
        ContractType contractType;

        tenantInfo = userProfile.getUserInfo(this.contractRequest.getTenantNickname(), UserInfoType.TENANT);
        renterInfo = userProfile.getUserInfo(this.nickname, UserInfoType.RENTER);
        contractType = ContractTypeJDBC.getIstance().getContractTypeById(this.contractRequest.getContractTypeId());



        this.contract = new Contract(this.contractRequest.getRentableId(), this.nickname,tenantInfo.getNickname(),
                this.contractRequest.getStartDate(), this.contractRequest.getEndDate(), tenantInfo.getName(),
                tenantInfo.getSurname(), tenantInfo.getCF(), tenantInfo.getDateOfBirth(), tenantInfo.getCityOfBirth(),
                tenantInfo.getAddress(), renterInfo.getName(), renterInfo.getSurname(), renterInfo.getCF(),
                renterInfo.getAddress(), this.contractRequest.getRentablePrice(), this.contractRequest.getDeposit(),
                contractType, this.contractRequest.getServices());
    }


    public void signContract() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException, ContractPeriodException {
        ContractBean contractBean = new ContractBean(this.contractRequest.getContractRequestId(), this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(), this.contract.getTenantDateOfBirth(), this.contract.getTenantCityOfBirth(), this.contract.getTenantAddress(), this.contract.getRenterName(), this.contract.getRenterSurname(), this.contract.getRenterCF(), this.contract.getRenterAddress(), this.contract.getGrossPrice(), this.contract.getNetPrice(), 1);

        System.out.println("Saving contract");

        ContractJDBC.getInstance().createContract(contractBean);
    }

    @Override
    public List<ContractLabelBean> getAllContracts() throws SQLException, ClassNotFoundException, ConfigFileException, ConfigException {
        List<ContractLabelBean> contractLabelBeans = new ArrayList<>();
        List<ContractId> contractIds;
        try {
            contractIds = ContractJDBC.getInstance().getAllContractsIdByRenterNickname(this.nickname);
        } catch (SQLException | ConfigException | ConfigFileException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        for (ContractId contractId : contractIds) {
            ContractsAndRequestRetriver contractsAndRequestRetriver = new ServiceDecorator(new ContractsAndRequestSimpleRetriver(contractId));

            Contract contract = contractsAndRequestRetriver.retriveContract();

            contractLabelBeans.add(new ContractLabelBean(contract.getContractId().getContractId(),
                    contract.getTenantNickname(), contract.getCreationDate(), contract.getStipulationDate(),
                    contract.getStartDate(), contract.getEndDate(), contract.getGrossPrice(), contract.getState()));

        }

        return contractLabelBeans;
    }

    public ContractInfoBean getContractInfo() {
        List<Service> services = this.contract.getServices();

        List<ServiceBean> serviceBeans = new ArrayList<>();

        services.forEach(service -> serviceBeans.add(new ServiceBean(service.getId(), service.getName(),
                service.getDescriprion(), service.getPrice())));

        return new ContractInfoBean(this.contract.getContractTypeName(), this.contract.getTenantNickname(),
                this.contract.getTenantName(), this.contract.getTenantSurname(), this.contract.getTenantCF(),
                this.contract.getCreationDate(), this.contract.getStipulationDate(), this.contract.getStartDate(),
                this.contract.getEndDate(), this.contract.getPropertyPrice(), this.contract.getDeposit(), serviceBeans,
                this.contract.getGrossPrice(), this.contract.getState());
    }
}
